package ch.saunah.saunahbackend.service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.webjars.NotFoundException;

import ch.saunah.saunahbackend.dto.SaunaTypeBody;
import ch.saunah.saunahbackend.model.Sauna;
import ch.saunah.saunahbackend.model.SaunaImage;
import ch.saunah.saunahbackend.repository.SaunaImageRepository;
import ch.saunah.saunahbackend.repository.SaunaRepository;
import ch.saunah.saunahbackend.util.ImageUpload;
import ch.saunah.saunahbackend.util.ImageUploadLocal;

/**
 * This class contains creating, removing, editing and get methods for saunas
 */
@Service
public class SaunaService {

    @Autowired
    private SaunaRepository saunaRepository;
    @Autowired
    private SaunaImageRepository saunaImageRepository;
    @Autowired
    private ImageUpload imageUploadUtil;

    public static final String SAUNA_IMAGES_DIR = "sauna-images";
    public static final int MAX_IMAGE_SIZE = 1024 * 1024 * 5;

    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * Add a new Sauna to the database
     * @param saunaTypeBody the required parameters for creating a sauna
     * @return the newly created sauna object
     * @throws NullPointerException
     */
    public Sauna addSauna(SaunaTypeBody saunaTypeBody) throws NullPointerException {
        Objects.requireNonNull(saunaTypeBody, "SaunaTypeBody must not be null!");
        Objects.requireNonNull(saunaTypeBody.getName(), "Name must not be null!");
        Objects.requireNonNull(saunaTypeBody.getDescription(), "Description must not be null!");
        Objects.requireNonNull(saunaTypeBody.getLocation(), "Location must not be null!");
        Objects.requireNonNull(saunaTypeBody.getStreet(), "Street must not be null!");

        Sauna sauna = new Sauna();
        setSaunaFields(sauna, saunaTypeBody);

        return saunaRepository.save(sauna);
    }

    /**
     * Delete an existing sauna from the database
     * @param id The id of the sauna that will be deleted
     * @return if the sauna has been successfully been deleted
     */
    public String removeSauna(int id) {
        saunaRepository.deleteById(id);
        return String.format("The sauna was with id %s has been removed", id);
    }

    /**
     * Edit an already existing sauna
     * @param id the id of the sauna to be edited
     * @param saunaTypeBody the parameter that shall be changed
     * @return the sauna that has been edited
     */
    public Sauna editSauna(int id,SaunaTypeBody saunaTypeBody) {
        Sauna editSauna = getSauna(id);
        setSaunaFields(editSauna, saunaTypeBody);
        return saunaRepository.save(editSauna);
    }

    private Sauna setSaunaFields(Sauna sauna, SaunaTypeBody saunaTypeBody) {
        sauna.setName(saunaTypeBody.getName());
        sauna.setDescription(saunaTypeBody.getDescription());
        sauna.setIsMobile(saunaTypeBody.isMobile());
        sauna.setMaxTemp(saunaTypeBody.getMaxTemp());
        sauna.setPrice(saunaTypeBody.getPrice());
        sauna.setNumberOfPeople(saunaTypeBody.getNumberOfPeople());
        sauna.setStreet(saunaTypeBody.getStreet());
        sauna.setLocation(saunaTypeBody.getLocation());
        sauna.setZip(saunaTypeBody.getZip());
        sauna.setType(saunaTypeBody.getType());
        sauna.setGoogleCalendarId(saunaTypeBody.getGoogleCalendarId());
        return sauna;
    }

    /**
     * Returns the sauna from the database from the specified id.
     *
     * @param id the sauna id
     * @return the found sauna from the database
     * @throws NotFoundException throws when no sauna was found with the specified id.
     */
    public Sauna getSauna(int id) throws NotFoundException {
        Sauna sauna = saunaRepository.findById(id).orElse(null);
        if (sauna == null){
            throw new NotFoundException(String.format("Sauna with id %d not found!", id));
        }
        return sauna;
    }

    /**
     * Returns all saunas from the database.
     *
     * @return all saunas from the database
     */
    public List<Sauna> getAllSauna() {
        return (List<Sauna>) saunaRepository.findAll();
    }

    /**
     * Returns the image of the specified fileName.
     *
     * @param fileName the fileName of the image
     * @return byte array of the image
     * @throws IOException throws when the image cant be converted to a byte array.
     */
    public ResponseEntity<byte[]> getImage(String fileName) throws IOException {
        String[] extensionComponents = fileName.split("\\.");
        if (extensionComponents.length < 1) {
            throw new IOException("MimeType of file could not be determined from extension.");
        }

        MediaType mediaType;
        try {
            String extension = extensionComponents[extensionComponents.length-1];
            String mimeType = MimeMappings.DEFAULT.get(extension);
            mediaType = MediaType.valueOf(mimeType);
        } catch (InvalidMediaTypeException e) {
            throw new IOException("MimeType of file could not be found.");
        }

        if (imageUploadUtil instanceof ImageUploadLocal) {
            ImageUploadLocal imageUploadLocal = (ImageUploadLocal) imageUploadUtil;
            return ResponseEntity.ok().contentType(mediaType).body(imageUploadLocal.getImageData(SAUNA_IMAGES_DIR, fileName));
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
    }

    /**
     * Returns all the images which belong to the sauna with the specified id.
     *
     * @param saunaId the sauna id
     * @return all images from the sauna
     * @throws NotFoundException throws when no sauna was found with the specified id.
     */
    public List<SaunaImage> getSaunaImages(int saunaId) throws NotFoundException {
        Sauna sauna = getSauna(saunaId);
        return saunaImageRepository.findBySaunaId(sauna.getId());
    }

    /**
     * Removes the image with the specified id.
     *
     * @param id the id of the saunaImage
     * @throws NotFoundException throws when no sauna was found with the specified id.
     * @throws IOException throws when the image can not be removed.
     */
    public void removeSaunaImage(int id) throws NotFoundException, IOException {
        SaunaImage image = saunaImageRepository.findById(id).orElse(null);
        if (image == null){
            throw new NotFoundException(String.format("Image with id %d not found!", id));
        }
        imageUploadUtil.removeImage(SAUNA_IMAGES_DIR, image.getFileName());
        saunaImageRepository.deleteById(id);
    }

    /**
     * Adds the images to the specified sauna.
     *
     * @param saunaId the sauna id
     * @param images the images
     * @throws NotFoundException throws when no sauna was found with the specified id.
     * @throws NullPointerException throw when the images object is null.
     * @throws IOException throws when Image could not be safed.
     */
    public void addSaunaImages(int saunaId, List<MultipartFile> images) throws NotFoundException, NullPointerException, IOException {
        Sauna sauna = getSauna(saunaId);
        if (images == null){
            throw new NullPointerException("No images were sent to add!");
        }
        for (MultipartFile image : images) {
            addImageSauna(sauna, image);
        }
    }

    private void addImageSauna(Sauna sauna, MultipartFile image) throws NullPointerException, IOException {
        Objects.requireNonNull(sauna, "Sauna must not be null!");
        if (image.getSize() > MAX_IMAGE_SIZE){
            throw new IOException("The image is too large to be saved!");
        }

        String mimeType = image.getContentType();
        Optional<MimeMappings.Mapping> mimeMaping = MimeMappings.DEFAULT.getAll().stream().filter(m -> m.getMimeType().equals(mimeType)).findFirst();
        if (!mimeMaping.isPresent()) {
            throw new IOException(String.format("No matching extension found for the mime type %s", mimeType));
        }

        String extension = mimeMaping.get().getExtension();
        String fileName = String.format(
            "image_file_%d_%s.%s",
            sauna.getId(),
            UUID.randomUUID(),
            extension
        );

        imageUploadUtil.saveImage(SAUNA_IMAGES_DIR, fileName, image);
        SaunaImage saunaImage = new SaunaImage();
        saunaImage.setFileName(fileName);
        saunaImage.setSauna(sauna);
        saunaImageRepository.save(saunaImage);
    }

}
