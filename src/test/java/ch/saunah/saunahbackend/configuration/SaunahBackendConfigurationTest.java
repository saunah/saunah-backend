package ch.saunah.saunahbackend.configuration;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SaunahBackendConfigurationTest {

    private static final String FRONTEND_URL = "https://booking.saunah.ch";
    private static final String[] EXTRA_CORS = new String[]{"https://localhost:3000", "https://localhost:3001"};

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

}

