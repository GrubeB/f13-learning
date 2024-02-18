package pl.app.learning.chapter.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.learning.chapter.service.ChapterSnapshotService;

import java.util.UUID;

@RestController
@RequestMapping(ChapterSnapshotController.resourcePath)
@RequiredArgsConstructor
@Getter
public class ChapterSnapshotController {
    public static final String resourceName = "snapshots";
    public static final String resourcePath = "/api/v1/chapters/{chapterId}/" + resourceName;

    public static final String revertSnapshotPath = "/{snapshotNumber}/revert";

    public final ChapterSnapshotService service;

    @PostMapping(revertSnapshotPath)
    ResponseEntity<?> fetchByIdAndDto(@PathVariable UUID chapterId, @PathVariable Long snapshotNumber) {
        service.revertSnapshot(chapterId, snapshotNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}
