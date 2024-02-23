package pl.app.property.accommodation_type.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.query_controller.QueryController;
import pl.app.property.accommodation_type.query.AccommodationTypeQueryService;
import pl.app.property.accommodation_type.query.model.AccommodationTypeQuery;

import java.util.UUID;

@RestController
@RequestMapping(AccommodationTypeQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class AccommodationTypeQueryController implements
        QueryController.DtoFetchable.Full<UUID, AccommodationTypeQuery> {
    public static final String resourceName = "accommodation-types";
    public static final String resourcePath = "/api/v1/" + resourceName;
    public final AccommodationTypeQueryService service;
}
