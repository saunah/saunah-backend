package ch.saunah.saunahbackend.controller;

import ch.saunah.saunahbackend.model.SaunaImage;
import ch.saunah.saunahbackend.repository.SaunaImageRepository;
import ch.saunah.saunahbackend.util.ImageUploadUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class ImageController {

    private final String SAUNA_IMAGES_DIR = "sauna-images/";

    @Autowired
    private SaunaImageRepository saunaImageRepository;
    private ImageUploadUtil imageUploadUtil = new ImageUploadUtil();

    @Operation(description = "Registers an account and sends a verification mail to the specified mail.")
    @PostMapping(value = "/sauna/{saunaId}/saveImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> saveImages(@PathVariable("saunaId") int saunaId, @RequestBody List<MultipartFile> images) throws IOException {
        for (MultipartFile image : images) {
            String filename = String.format("image_file_%d_%s.png", saunaId, UUID.randomUUID());
            imageUploadUtil.saveFile(SAUNA_IMAGES_DIR, filename, image);
            createSaunaImage(saunaId, filename);
        }
        return ResponseEntity.ok("success");
    }

    private void createSaunaImage(int saunaId, String fileName){
        SaunaImage image = new SaunaImage();
        image.setSaunaId(saunaId);
        image.setFilePath(fileName);
        saunaImageRepository.save(image);
    }

    @RequestMapping(value = "/sauna/images/{fileName}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImage(@PathVariable("fileName") String fileName) throws IOException {
        byte[] image = imageUploadUtil.getFile(SAUNA_IMAGES_DIR, fileName);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image);
    }

    @RequestMapping(value = "/sauna/{saunaId}/images", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getSaunaImages(@PathVariable("saunaId") int saunaId) throws IOException {
        var images =  saunaImageRepository.findBySaunaId(saunaId);
        List<String> imageFiles = images.stream().map(x -> x.getFilePath()).collect(Collectors.toList());
        return ResponseEntity.ok().body(imageFiles);
    }
}
