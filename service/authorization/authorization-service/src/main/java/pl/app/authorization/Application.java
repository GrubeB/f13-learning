package pl.app.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import pl.app.file.FileConfiguration;

@SpringBootApplication
@EntityScan(basePackages = {
        "pl.app.file.file.application.domain",
        "pl.app.authorization"
})
@Import({
        FileConfiguration.class
})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
