package ch.saunah.saunahbackend.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageUpload {

    void saveImage(String directory, String fileName, MultipartFile multipartFile) throws IOException;

    /**
     * Reads the file and returns the image as byte array.
     *
     * @param directory the directory where the file is stored
     * @param fileName the filename of the image
     * @return image byte array
     * @throws IOException throws when the path is not valid
     */
    byte[] getImage(String directory, String fileName) throws IOException;

    /**
     * Deletes the image file on the specified directory.
     *
     * @param directory the directory where the file is stored
     * @param fileName the filename of the image
     */
    void removeImage(String directory, String fileName) throws IOException;
}
