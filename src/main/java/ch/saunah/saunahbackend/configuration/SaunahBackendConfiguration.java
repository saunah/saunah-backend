package ch.saunah.saunahbackend.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

public class SaunahBackendConfiguration {
    @Value("${saunah.frontend.baseurl}")
    private String frontendBaseUrl;

    @Value("${saunah.version}")
    private String version;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins(frontendBaseUrl);
            }
        };
    }

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .info(
                    new Info()
                    .title("SauNah API Docs")
                    .description("Documentation of API Routes for the SauNah backend.")
                    .version(version)
                );
    }
}
