package ch.saunah.saunahbackend.util;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class is used as a helper class to save and read images from a directory.
 */
public class ImageUploadUtil implements ImageUpload {

    @Value("${saunah.object.storage.bucket.id}")
    private String bucket;
    @Value("${saunah.object.storage.bucket.endpoint}")
    private String endpoint;
    @Value("${saunah.object.storage.bucket.key}")
    private String key;
    @Value("${saunah.object.storage.bucket.secret.key}")
    private String secret_key;

    @Value("${saunah.object.storage.bucket.region}")
    private String region;

    private AmazonS3 getObjectStorageClient(){
        AWSCredentials credentials = new BasicAWSCredentials(key, secret_key);
        AmazonS3 s3client = AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
            .build();
        return s3client;
    }

    private String getObjectPath(String directory, String fileName){
        Path uploadPath = Paths.get(directory);
        Path filePath = uploadPath.resolve(fileName);
        return filePath.toString();
    }

    /**
     * Saves the image to the specified directory.
     *
     * @param directory directory where image will be safed
     * @param fileName the fileName of the image
     * @param multipartFile the image object
     * @throws IOException throws when Path is not valid
     */
    public void saveImage(String directory, String fileName, MultipartFile multipartFile) throws IOException {
        try {
            AmazonS3 client = getObjectStorageClient();
            if (!client.doesBucketExist(bucket)) {
                client.createBucket(bucket);
            }
            ObjectMetadata data = new ObjectMetadata();
            data.setContentType(multipartFile.getContentType());
            data.setContentLength(multipartFile.getSize());
            client.putObject(bucket, getObjectPath(directory, fileName), multipartFile.getInputStream(), data);
        }
        catch (AmazonClientException e){
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Reads the file and returns the image as byte array.
     *
     * @param directory the directory where the file is stored
     * @param fileName the filename of the image
     * @return image byte array
     * @throws IOException throws when the path is not valid
     */
    public byte[] getImage(String directory, String fileName) throws IOException {
        try {
            AmazonS3 client = getObjectStorageClient();
            if (!client.doesBucketExist(bucket)) {
                throw new IOException("Bucket does not exist and no image can be found!");
            }
            S3Object s3object = client.getObject(bucket, getObjectPath(directory, fileName));
            S3ObjectInputStream inputStream = s3object.getObjectContent();
            return inputStream.readAllBytes();
        } catch (AmazonClientException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Deletes the image file on the specified directory.
     *
     * @param directory the directory where the file is stored
     * @param fileName the filename of the image
     */
    public void removeImage(String directory, String fileName) throws IOException {
        try {
        AmazonS3 client = getObjectStorageClient();
        if (!client.doesBucketExist(bucket)) {
            throw new IOException("Bucket does not exist and no image can be found!");
        }
        client.deleteObject(bucket, getObjectPath(directory, fileName));
        } catch (AmazonClientException e) {
            throw new IOException(e.getMessage());
        }
    }
}
