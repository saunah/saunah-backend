package ch.saunah.saunahbackend.util;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

/**
 * This class is used as a helper class to save and read images from
 * S3 object storage.
 */
public class ImageUploadS3 implements ImageUpload {

    @Value("${saunah.object-storage.bucket.id}")
    private String bucket;
    @Value("${saunah.object-storage.bucket.endpoint}")
    private String endpoint;
    @Value("${saunah.object-storage.bucket.access-key}")
    private String accessKey;
    @Value("${saunah.object-storage.bucket.secret-key}")
    private String secretKey;

    @Value("${saunah.object-storage.bucket.region}")
    private String region;

    private AmazonS3 getObjectStorageClient(){
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
            .build();
    }

    private String getObjectPath(String directory, String fileName){
        Path uploadPath = Paths.get(directory);
        Path filePath = uploadPath.resolve(fileName);
        return filePath.toString();
    }

    /**
     * Saves the image to the specified S3 bucket.
     *
     * @param directory directory where image will be saved
     * @param filename the fileName of the image
     * @param multipartFile the image object
     * @throws IOException throws when Path is not valid
     */
    public void saveImage(String directory, String filename, MultipartFile multipartFile) throws IOException {
        try {
            AmazonS3 client = getObjectStorageClient();
            if (!client.doesBucketExist(bucket)) {
                client.createBucket(bucket);
            }
            ObjectMetadata data = new ObjectMetadata();
            data.setContentType(multipartFile.getContentType());
            data.setContentLength(multipartFile.getSize());

            PutObjectRequest putRequest = new PutObjectRequest(
                bucket,
                getObjectPath(directory, filename),
                multipartFile.getInputStream(),
                data
            ).withCannedAcl(CannedAccessControlList.PublicRead);

            client.putObject(putRequest);
        }
        catch (AmazonClientException e){
            throw new IOException(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public URL getImageURL(String directory, String filename) throws IOException {
        AmazonS3 client = getObjectStorageClient();
        return client.getUrl(bucket, getObjectPath(directory, filename));
    }

    /**
     * Deletes the image file on the specified S3 path.
     *
     * @param directory the directory where the file is stored
     * @param fileName the filename of the image
     */
    public void removeImage(String directory, String fileName) throws IOException {
        try {
        AmazonS3 client = getObjectStorageClient();
        client.deleteObject(bucket, getObjectPath(directory, fileName));
        } catch (AmazonClientException e) {
            throw new IOException(e.getMessage());
        }
    }
}
