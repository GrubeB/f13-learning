package pl.app.learning.chapter.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.command_controller.CommandController;
import pl.app.learning.chapter.model.ChapterEntity;
import pl.app.learning.chapter.service.ChapterService;

import java.util.UUID;

@RestController
@RequestMapping(ChapterController.resourcePath)
@RequiredArgsConstructor
@Getter
public class ChapterController implements
        CommandController.Creatable.SimpleCreatable<UUID, ChapterEntity>,
        CommandController.Updatable.SimpleUpdatable<UUID, ChapterEntity>,
        CommandController.Deletable.SimpleDeletable<UUID, ChapterEntity> {
    public static final String resourceName = "chapters";
    public static final String resourcePath = "/api/v1/" + resourceName;

    public final ChapterService service;
}
