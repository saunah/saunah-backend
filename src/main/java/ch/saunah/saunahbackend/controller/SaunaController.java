package ch.saunah.saunahbackend.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import ch.saunah.saunahbackend.dto.SaunaImageResponse;
import ch.saunah.saunahbackend.dto.SaunaResponse;
import ch.saunah.saunahbackend.dto.SaunaTypeBody;
import ch.saunah.saunahbackend.dto.SaunahApiResponse;
import ch.saunah.saunahbackend.model.SaunaImage;
import ch.saunah.saunahbackend.service.SaunaService;
import ch.saunah.saunahbackend.util.ImageUpload;
import ch.saunah.saunahbackend.util.ImageUploadLocal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Controls the different operation that can be done with sauna types
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class SaunaController {
    private static final String RESPONSE_SUCCESS = "success";

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private SaunaService saunaService;

    @Autowired
    private ImageUpload imageUploadUtil;

    @Operation(description = "Allows adding a new Sauna type.")
    @PostMapping(path = "saunas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Sauna created", content = @Content(schema = @Schema(implementation = SaunaResponse.class))),
        @ApiResponse(responseCode = "400", description = "Bad request, set fields do not match with the conditions", content = @Content(schema = @Schema(implementation = SaunahApiResponse.class))),
    })
    public @ResponseBody
    ResponseEntity<SaunaResponse> createSauna(@RequestBody SaunaTypeBody saunaTypeBody) throws NullPointerException{
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new SaunaResponse(saunaService.addSauna(saunaTypeBody)));
    }

    @Operation(description = "Returns a list of saunas.")
    @GetMapping(path="saunas")
    public @ResponseBody List<SaunaResponse> getAllSauna() {
        return saunaService.getAllSauna().stream().map(SaunaResponse::new).collect(Collectors.toList());
    }

    @Operation(description = "Returns the sauna with the ID specified.")
    @GetMapping(path="saunas/{id}")
    public @ResponseBody ResponseEntity<SaunaResponse> getSauna(@PathVariable(value = "id", required = true) Integer id) throws NotFoundException {
        return ResponseEntity.ok(new SaunaResponse(saunaService.getSauna(id)));
    }

    @Operation(description = "Allows removing a existing Sauna with the ID specified.")
    @DeleteMapping(path = "saunas/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sauna deleted"),
        @ApiResponse(responseCode = "400", description = "Sauna does not exist", content = @Content(schema = @Schema(implementation = SaunahApiResponse.class))),
    })
    public @ResponseBody ResponseEntity<String> removeSauna(@PathVariable("id") int id) throws IllegalArgumentException {
        saunaService.removeSauna(id);
        return ResponseEntity.ok(RESPONSE_SUCCESS);
    }

    @Operation(description = "Allows editing an existing Sauna type.")
    @PutMapping(path = "saunas/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sauna edited", content = @Content(schema = @Schema(implementation = SaunaResponse.class))),
        @ApiResponse(responseCode = "400", description = "Bad request, set fields do not match with the conditions", content = @Content(schema = @Schema(implementation = SaunahApiResponse.class))),
    })
    public @ResponseBody
    ResponseEntity<SaunaResponse> editSauna(@PathVariable("id") int id, @RequestBody SaunaTypeBody saunaTypeBody) throws NotFoundException {
        return ResponseEntity.ok(new SaunaResponse(saunaService.editSauna(id, saunaTypeBody)));
    }

    @Operation(description = "Adds new images to the corresponding sauna.")
    @PostMapping(value = "/saunas/{id}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Image saved"),
        @ApiResponse(responseCode = "400", description = "Sauna does not exist", content = @Content(schema = @Schema(implementation = SaunahApiResponse.class))),
    })
    @ResponseBody
    public ResponseEntity<String> saveImages(@PathVariable("id") int saunaId, @RequestBody List<MultipartFile> images) throws NotFoundException, NullPointerException, IOException{
        saunaService.addSaunaImages(saunaId, images);
        return ResponseEntity.ok(RESPONSE_SUCCESS);
    }


    @Operation(description = "Removes the image of the specified Id.")
    @DeleteMapping(path = "saunas/images/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Image deleted"),
        @ApiResponse(responseCode = "400", description = "Image does not exist", content = @Content(schema = @Schema(implementation = SaunahApiResponse.class))),
    })
    @ResponseBody
    public ResponseEntity<String> removeImage(@PathVariable("id") int imageId) throws NotFoundException, IOException{
        saunaService.removeSaunaImage(imageId);
        return ResponseEntity.ok(RESPONSE_SUCCESS);
    }

    @Operation(description = "Returns the image file of the corresponding file name.")
    @GetMapping(value = "/saunas/images/{fileName}")
    @ResponseBody
    public ResponseEntity<byte[]> getImage(@PathVariable("fileName") String fileName) throws IOException {
        return saunaService.getImage(fileName);
    }

    @Operation(description = "Returns a list of all the images of a sauna with all their data.")
    @GetMapping(value = "/saunas/{id}/images")
    @ResponseBody
    public ResponseEntity<List<SaunaImageResponse>> getSaunaImages(@PathVariable("id") int saunaId) throws NotFoundException {
        List<SaunaImageResponse> saunaImages = saunaService.getSaunaImages(saunaId)
            .stream().map(image -> new SaunaImageResponse(
                image.getId(),
                saunaId,
                getImageUrl(image).toString()
            )).collect(Collectors.toList());
        return ResponseEntity.ok(saunaImages);
    }

    /**
     * Retrieves the URL for the image passed image and returns it.
     * @param saunaImage the image to get the URL for.
     * @return the URL of the image.
     * @throws IOException
     */
    @Nullable
    private URL getImageUrl(SaunaImage saunaImage) {
        String imageDir = (imageUploadUtil instanceof ImageUploadLocal)
            ? "saunas/images"
            : SaunaService.SAUNA_IMAGES_DIR;
        URL url = null;
        try {
            url = imageUploadUtil.getImageURL(imageDir, saunaImage.getFileName());
        } catch (IOException e) {
            logger.error("Image URL could not be retrieved for SaunaImage", e);
        }
        return url;
    }

}



