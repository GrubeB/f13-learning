package pl.app.file;

import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import pl.app.file.file.adapter.in.FileController;
import pl.app.file.file.adapter.out.FileRepositoryImpl;
import pl.app.file.file.application.port.in.FileService;
import pl.app.file.file.application.port.out.FileRepository;
import pl.app.file.file.application.service.FileServiceImpl;
import pl.app.file.file_storage.adapter.out.LocalStorageRepository;
import pl.app.file.file_storage.application.port.in.FileStorageService;
import pl.app.file.file_storage.application.port.out.FileStorageRepository;
import pl.app.file.file_storage.application.service.FileStorageServiceImpl;

import java.io.IOException;

public class FileConfiguration {

    // FILE_STORAGE - pl.app.file.file_storage
    @Bean
    FileStorageRepository fileStorageRepository(Environment environment) throws IOException {
        return new LocalStorageRepository(environment);
    }

    @Bean
    FileStorageService fileStorageService(FileStorageRepository fileStorageRepository) {
        return new FileStorageServiceImpl(fileStorageRepository);
    }

    // FILE - pl.app.file.file
    @Bean
    FileRepository fileRepository(EntityManager entityManager) {
        return new FileRepositoryImpl(entityManager);
    }

    @Bean
    FileService fileService(FileRepository fileRepository, FileStorageService fileStorageService, Environment environment) {
        return new FileServiceImpl(fileRepository, fileStorageService, environment);
    }
    @Bean
    FileController fileController(FileService fileService){
        return new FileController(fileService);
    }
}
