package ch.saunah.saunahbackend.service;

import ch.saunah.saunahbackend.dto.SaunaTypeBody;
import ch.saunah.saunahbackend.model.Sauna;
import ch.saunah.saunahbackend.model.SaunaImage;
import ch.saunah.saunahbackend.repository.SaunaImageRepository;
import ch.saunah.saunahbackend.repository.SaunaRepository;
import ch.saunah.saunahbackend.util.ImageUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * This class contains creating, removing, editing and get methods for saunas
 */
@Service
public class SaunaService {

    @Autowired
    private SaunaRepository saunaRepository;
    @Autowired
    private SaunaImageRepository saunaImageRepository;

    private final String SAUNA_IMAGES_DIR = "sauna-images/";

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
        sauna.setType(saunaTypeBody.getType());
        sauna.setMaxTemp(saunaTypeBody.getMaxTemp());
        sauna.setPrize(saunaTypeBody.getPrize());
        sauna.setNumberOfPeople(saunaTypeBody.getNumberOfPeople());
        sauna.setStreet(saunaTypeBody.getStreet());
        sauna.setLocation(saunaTypeBody.getLocation());
        sauna.setPlz(saunaTypeBody.getPlz());
        return sauna;
    }

    public Sauna getSauna(int id) {
        Sauna sauna = saunaRepository.findById(id).orElse(null);
        if (sauna == null){
            throw new NotFoundException(String.format("Sauna with id %d not found!", id));
        }
        return sauna;
    }

    public Iterable<Sauna> getAllSauna() {
        return saunaRepository.findAll();
    }

    public ResponseEntity<byte[]> getImage(String fileName) throws IOException {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(ImageUploadUtil.getImage(SAUNA_IMAGES_DIR, fileName));
    }

    public List<SaunaImage> getSaunaImages(int saunaId) {
        Sauna sauna = getSauna(saunaId);
        return saunaImageRepository.findBySaunaId(sauna.getId());
    }

    public void removeSaunaImage(int id) {
        SaunaImage image = saunaImageRepository.findById(id).orElse(null);
        if (image == null){
            throw new NotFoundException(String.format("Image with id %d not found!", id));
        }
        saunaImageRepository.deleteById(id);
    }

    public void addSaunaImages(int saunaId, List<MultipartFile> images) throws NotFoundException, NullPointerException {
        Sauna sauna = getSauna(saunaId);
        if (images == null){
            throw new NullPointerException("No images were sent to add!");
        }
        for (MultipartFile image : images) {
            addImageSauna(sauna, image);
        }
    }

    private void addImageSauna(Sauna sauna, MultipartFile image) throws NullPointerException{
        Objects.requireNonNull(sauna, "Sauna must not be null!");
        String fileName = String.format("image_file_%d_%s.png", sauna.getId(), UUID.randomUUID());
        try {
            ImageUploadUtil.saveImage(SAUNA_IMAGES_DIR, fileName, image);
            SaunaImage saunaImage = new SaunaImage();
            saunaImage.setFileName(fileName);
            saunaImage.setSauna(sauna);
            saunaImageRepository.save(saunaImage);
        } catch (IOException e) {
            System.err.printf("Error while saving file %s in %s: %s", fileName, SAUNA_IMAGES_DIR, e.getMessage());
        }
    }

}
