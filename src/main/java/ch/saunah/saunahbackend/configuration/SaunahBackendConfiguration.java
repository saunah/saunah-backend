package ch.saunah.saunahbackend.configuration;

import java.util.Arrays;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SaunahBackendConfiguration {
    @Value("${saunah.frontend.baseurl}")
    private String frontendBaseUrl;

    @Value("${saunah.version}")
    private String version;

    @Nullable
    @Value("${saunah.extra-allowed-cors}")
    private String extraAllowedCors;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins(
                    getAllowedCors(frontendBaseUrl, extraAllowedCors));
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

    /**
     * Get all allowed CORS, which includes frontend URL
     * and extra allowed cors defined.
     * @return
     */
    public static String[] getAllowedCors(String frontend, @Nullable String extra) {
        Stream<String> frontendStream = Stream.of(frontend.trim());
        Stream<String> extraStream = Arrays.stream(
            (extra != null && !extra.isEmpty()) ? extra.split(",") : new String[]{})
            .map(String::trim);
        return Stream.concat(frontendStream, extraStream).toArray(String[]::new);
    }
}
