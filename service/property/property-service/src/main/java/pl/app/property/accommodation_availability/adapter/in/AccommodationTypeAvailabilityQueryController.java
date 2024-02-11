package pl.app.property.accommodation_availability.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.query_controller.QueryController;
import pl.app.property.accommodation_availability.adapter.out.persistence.AccommodationTypeAvailabilityEntity;
import pl.app.property.accommodation_availability.query.AccommodationTypeAvailabilityQueryService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(AccommodationTypeAvailabilityQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class AccommodationTypeAvailabilityQueryController implements
        QueryController.DtoFetchable.Full<UUID, AccommodationTypeAvailabilityEntity> {

    public static final String resourceName = "accommodation-type-availabilities";
    public static final String resourcePath = "/api/v1/" + resourceName;

    private final AccommodationTypeAvailabilityQueryService service;
}
