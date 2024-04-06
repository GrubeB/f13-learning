package pl.app.file.file.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.app.file.file.application.port.in.FileService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping(FileController.resourcePath)
@RequiredArgsConstructor
public class FileController {
    public static final String resourceName = "files";
    public static final String resourcePath = "/api/v1/" + resourceName;

    private final FileService service;

    @GetMapping(value = "/{fileId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Resource getFileViaByteArrayResource(@PathVariable UUID fileId) {
        return service.fetchFileAsResourceById(fileId);
    }
}
