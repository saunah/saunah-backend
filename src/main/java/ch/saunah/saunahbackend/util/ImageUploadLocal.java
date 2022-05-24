package ch.saunah.saunahbackend.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * This class is used as a helper class to save and read images from a directory.
 */
public class ImageUploadLocal implements ImageUpload {

    /**
     * Saves the image to the specified directory.
     *
     * @param directory directory where image will be saved
     * @param fileName the fileName of the image
     * @param multipartFile the image object
     * @throws IOException throws when Path is not valid
     */
    public void saveImage(String directory, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(directory);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Could not save image file: " + fileName, e);
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
    public byte[] getImageData(String directory, String fileName) throws IOException {
        Path uploadPath = Paths.get(directory);
        Path filePath = uploadPath.resolve(fileName);
        File file = filePath.toFile();
        byte[] arr = new byte[(int) file.length()];
        try (FileInputStream fl = new FileInputStream(file)) {
            int bytesRead = fl.read(arr);
            if (bytesRead != file.length()){
                throw new IOException("Error while reading file! The bytes read are not equal to the file size!");
            }
        }
        return arr;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public URL getImageURL(String directory, String filename) throws IOException {
        return getBaseURI().pathSegment(directory, filename).build().toUri().toURL();
    }

    /**
     * Deletes the image file on the specified directory.
     *
     * @param directory the directory where the file is stored
     * @param fileName the filename of the image
     */
    public void removeImage(String directory, String fileName) throws IOException {
        try {
            Path uploadPath = Paths.get(directory);
            Path filePath = uploadPath.resolve(fileName);
            File file = filePath.toFile();
            if (!file.delete()){
                throw new IOException("File was not successfully deleted");
            }
        }
        catch (Exception e){
            throw new IOException("File was not successfully deleted");
        }
    }

    private UriComponentsBuilder getBaseURI() {
        return ServletUriComponentsBuilder.fromCurrentContextPath();
    }

}
