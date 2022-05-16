package ch.saunah.saunahbackend.configuration;

import ch.saunah.saunahbackend.util.ImageUpload;
import ch.saunah.saunahbackend.util.ImageUploadLocal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SaunahTestConfig {

    @Bean
    public ImageUpload image() {
        return new ImageUploadLocal();
    }
}
