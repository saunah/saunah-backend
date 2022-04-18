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
public class ImageUploadUtil {

    /**
     * Saves the image to the specified directory.
     *
     * @param uploadDir directory where image will be safed
     * @param fileName the fileName of the image
     * @param multipartFile the image object
     * @throws IOException throws when Path is not valid
     */
    public static void saveImage(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
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
    public static byte[] getImage(String directory, String fileName) throws IOException {
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
}
