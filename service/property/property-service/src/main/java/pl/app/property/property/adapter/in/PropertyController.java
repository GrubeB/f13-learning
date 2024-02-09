package pl.app.property.property.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.command_controller.CommandController;
import pl.app.property.property.application.domain.model.PropertyEntity;
import pl.app.property.property.application.port.in.PropertyService;

import java.util.UUID;

@RestController
@RequestMapping(PropertyController.resourcePath)
@RequiredArgsConstructor
@Getter
public class PropertyController implements
        CommandController.Creatable.SimpleCreatable<UUID, PropertyEntity>,
        CommandController.Updatable.SimpleUpdatable<UUID, PropertyEntity>,
        CommandController.Deletable.SimpleDeletable<UUID, PropertyEntity> {
    public static final String resourceName = "properties";
    public static final String resourcePath = "/api/v1/" + resourceName;

    public final PropertyService service;
}
