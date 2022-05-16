package ch.saunah.saunahbackend.configuration;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.stream.Stream;

import ch.saunah.saunahbackend.SaunahBackendApplication;
import ch.saunah.saunahbackend.util.ImageUpload;
import ch.saunah.saunahbackend.util.ImageUploadLocal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {SaunahBackendApplication.class, SaunahTestConfig.class} )
class SaunahBackendConfigurationTest {

    private static final String FRONTEND_URL = "https://booking.saunah.ch";
    private static final String[] EXTRA_CORS = new String[]{"https://localhost:3000", "https://localhost:3001"};

    @Autowired
    private ImageUpload imageUpload;

    @Test
    void testExtraCorsSet() {
        String[] actualCors = SaunahBackendConfiguration.getAllowedCors(
            FRONTEND_URL, String.join(",", EXTRA_CORS));

        String[] expectedCors = Stream.concat(
            Stream.of(FRONTEND_URL), Arrays.stream(EXTRA_CORS))
            .toArray(String[]::new);

        assertArrayEquals(expectedCors, actualCors);
    }

    @Test
    void testExtraCorsEmpty() {
        String[] actualCors = SaunahBackendConfiguration.getAllowedCors(
            FRONTEND_URL, String.join(",", ""));

        String[] expectedCors = new String[]{FRONTEND_URL};

        assertArrayEquals(expectedCors, actualCors);
    }

    @Test
    void testExtraCorsNull() {
        String[] actualCors = SaunahBackendConfiguration.getAllowedCors(
            FRONTEND_URL, null);
        String[] expectedCors = new String[]{FRONTEND_URL};

        assertArrayEquals(expectedCors, actualCors);
    }

    @Test
    void testImageUpload() {
        assertTrue(imageUpload instanceof ImageUploadLocal);
    }

}

