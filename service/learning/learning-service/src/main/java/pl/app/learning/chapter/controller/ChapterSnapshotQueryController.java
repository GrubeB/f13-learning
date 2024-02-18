package pl.app.learning.chapter.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.dto_criteria.Dto;
import pl.app.common.query_controller.QueryController;
import pl.app.learning.chapter.model.ChapterEntity;
import pl.app.learning.chapter.model.ChapterEntitySnapshot;
import pl.app.learning.chapter.service.ChapterQueryService;
import pl.app.learning.chapter.service.ChapterSnapshotQueryService;

import java.util.UUID;

@RestController
@RequestMapping(ChapterSnapshotQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class ChapterSnapshotQueryController {
    public static final String resourceName = "snapshots";
    public static final String resourcePath = "/api/v1/chapters/{chapterId}/" + resourceName;

    public final ChapterSnapshotQueryService service;

    @GetMapping
    ResponseEntity<?> fetchByIdAndDto(@PathVariable UUID chapterId, Dto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getService().findByOwnerId(chapterId));
    }
}
