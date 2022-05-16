package ch.saunah.saunahbackend.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * This class is used as a helper class to save and read images from a directory.
 */
public class ImageUploadLocal implements ImageUpload {

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
    public byte[] getImage(String directory, String fileName) throws IOException {
        Path uploadPath = Paths.get(directory);
        Path filePath = uploadPath.resolve(fileName);
        File file = filePath.toFile();
        byte[] arr = new byte[(int) file.length()];
        FileInputStream fl = new FileInputStream(file);
        try {
            int bytesRead = fl.read(arr);
            if (bytesRead != file.length()){
                throw new IOException("Error while reading file! The bytes read are not equal to the file size!");
            }
            fl.close();
        }
        catch (Exception e){
            fl.close();
        }
        return arr;
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
            file.delete();
            if (file.delete()){
                System.err.printf("File %s successfully deleted.", fileName);
            }
            else{
                System.err.printf("File %s not successfully deleted.", fileName);
            }
        }
        catch (Exception e){
            System.err.printf("Error while removing image: %s", e.getMessage());
        }
    }
}
