package ch.saunah.saunahbackend.util;

import java.io.IOException;
import java.net.URL;

import org.springframework.web.multipart.MultipartFile;

/**
 * This class is used as a helper class to save and read images.
 */
public interface ImageUpload {

    /**
     * Saves the image to the specified path.
     *
     * @param directory directory where image will be saved
     * @param fileName the fileName of the image
     * @param multipartFile the image object
     * @throws IOException throws when Path is not valid
     */
    void saveImage(String directory, String fileName, MultipartFile multipartFile) throws IOException;

    /**
     * Returns a HTTP URL of the image.
     * @param directory the directory where the file is stored
     * @param filename the filename of the image
     * @return URL of the image
     * @throws IOException if the path is not valid
     */
    URL getImageURL(String directory, String filename) throws IOException;

    /**
     * Deletes the image file on the specified directory.
     *
     * @param directory the directory where the file is stored
     * @param fileName the filename of the image
     */
    void removeImage(String directory, String fileName) throws IOException;
}
