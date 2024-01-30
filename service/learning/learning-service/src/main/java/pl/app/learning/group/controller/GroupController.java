package pl.app.learning.group.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.command_controller.CommandController;
import pl.app.learning.group.model.GroupEntity;
import pl.app.learning.group.service.GroupService;

import java.util.UUID;

@RestController
@RequestMapping(GroupController.resourcePath)
@RequiredArgsConstructor
@Getter
public class GroupController implements
        CommandController.Creatable.SimpleCreatable<UUID, GroupEntity>,
        CommandController.Updatable.SimpleUpdatable<UUID, GroupEntity>,
        CommandController.Deletable.SimpleDeletable<UUID, GroupEntity> {
    public static final String resourceName = "groups";
    public static final String resourcePath = "/api/v1/" + resourceName;

    public final GroupService service;
}
