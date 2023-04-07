package ua.glumaks.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class StorageConfigurationProperties {

    @Value("${upload.path-dir}")
    private String uploadDir;

}
