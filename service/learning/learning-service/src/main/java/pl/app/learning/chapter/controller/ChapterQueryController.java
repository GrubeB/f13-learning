package pl.app.learning.chapter.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.query_controller.QueryController;
import pl.app.learning.chapter.model.ChapterEntity;
import pl.app.learning.chapter.service.ChapterQueryService;

import java.util.UUID;

@RestController
@RequestMapping(ChapterQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class ChapterQueryController implements
        QueryController.SimpleFetchable.Full<UUID, ChapterEntity> {
    public static final String resourceName = "chapters";
    public static final String resourcePath = "/api/v1/" + resourceName;

    public final ChapterQueryService service;
}
