package pl.app.property.property.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.query_controller.QueryController;
import pl.app.property.property.application.domain.model.PropertyEntity;
import pl.app.property.property.query.PropertyQueryService;

import java.util.UUID;

@RestController
@RequestMapping(PropertyQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class PropertyQueryController implements
        QueryController.SimpleFetchable.Full<UUID, PropertyEntity> {
    public static final String resourceName = "properties";
    public static final String resourcePath = "/api/v1/" + resourceName;

    public final PropertyQueryService service;
}