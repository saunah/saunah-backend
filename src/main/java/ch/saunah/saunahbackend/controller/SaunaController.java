package ch.saunah.saunahbackend.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ch.saunah.saunahbackend.dto.SaunaImageResponse;
import ch.saunah.saunahbackend.dto.SaunaResponse;
import ch.saunah.saunahbackend.dto.SaunaTypeBody;
import ch.saunah.saunahbackend.repository.SaunaRepository;
import ch.saunah.saunahbackend.service.SaunaService;
import io.swagger.v3.oas.annotations.Operation;

/**
 * Controls the different operation that can be done with sauna types
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class SaunaController {

    @Autowired
    private SaunaRepository saunaRepository;

    @Autowired
    private SaunaService saunaService;

    @Operation(description = "Allows adding a new Sauna type.")
    @PostMapping(path = "saunas")
    public @ResponseBody
    ResponseEntity<SaunaResponse> createSauna(@RequestBody SaunaTypeBody saunaTypeBody) {
        return ResponseEntity.ok(new SaunaResponse(saunaService.addSauna(saunaTypeBody)));
    }

    @Operation(description = "Returns a list of saunas.")
    @GetMapping(path="saunas")
    public @ResponseBody List<SaunaResponse> getAllSauna() {
        return saunaService.getAllSauna().stream().map(x -> new SaunaResponse(x)).collect(Collectors.toList());
    }

    @Operation(description = "Returns the sauna with the ID specified.")
    @GetMapping(path="saunas/{id}")
    public @ResponseBody ResponseEntity<SaunaResponse> getSauna(@PathVariable(value = "id", required = true) Integer id) {
        return ResponseEntity.ok(new SaunaResponse(saunaService.getSauna(id)));
    }

    @Operation(description = "Allows removing a existing Sauna with the ID specified.")
    @DeleteMapping(path = "saunas/{id}")
    public @ResponseBody ResponseEntity<String> removeSauna(@PathVariable("id") int id) {
        saunaService.removeSauna(id);
        return ResponseEntity.ok("success");
    }

    @Operation(description = "Allows editing an existing Sauna type.")
    @PutMapping(path = "saunas/{id}")
    public @ResponseBody
    ResponseEntity<SaunaResponse> editSauna(@PathVariable("id") int id, @RequestBody SaunaTypeBody saunaTypeBody) {
        return ResponseEntity.ok(new SaunaResponse(saunaService.editSauna(id, saunaTypeBody)));
    }

    @Operation(description = "Adds new images to the corresponding sauna.")
    @PostMapping(value = "/saunas/{id}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> saveImages(@PathVariable("id") int saunaId, @RequestBody List<MultipartFile> images) throws IOException {
        saunaService.addSaunaImages(saunaId, images);
        return ResponseEntity.ok("success");
    }


    @Operation(description = "Removes the image of the specified Id.")
    @DeleteMapping(path = "saunas/images/{id}")
    @ResponseBody
    public ResponseEntity<String> removeImage(@PathVariable("id") int imageId) throws IOException {
        saunaService.removeSaunaImage(imageId);
        return ResponseEntity.ok("success");
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
    public ResponseEntity<List<SaunaImageResponse>> getSaunaImages(@PathVariable("id") int saunaId) {
        List<SaunaImageResponse> saunaImages = saunaService.getSaunaImages(saunaId)
            .stream().map(x -> new SaunaImageResponse(x.getId(), saunaId, x.getFileName())).collect(Collectors.toList());
        return ResponseEntity.ok(saunaImages);
    }

}



