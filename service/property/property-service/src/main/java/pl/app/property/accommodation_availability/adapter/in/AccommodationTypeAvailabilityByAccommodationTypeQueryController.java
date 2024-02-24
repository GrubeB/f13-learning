package pl.app.property.accommodation_availability.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.query_controller.QueryController;
import pl.app.property.accommodation_availability.query.AccommodationTypeAvailabilityQueryService;
import pl.app.property.accommodation_availability.query.model.AccommodationTypeAvailabilityQuery;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(AccommodationTypeAvailabilityByAccommodationTypeQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class AccommodationTypeAvailabilityByAccommodationTypeQueryController implements
        QueryController.DtoFetchableWithFilter.Full<UUID, AccommodationTypeAvailabilityQuery> {

    public static final String resourceName = "accommodation-type-availabilities";
    public static final String resourcePath = "/api/v1/accommodation-types/{accommodationTypeId}/" + resourceName;
    public final AccommodationTypeAvailabilityQueryService service;
    private final Map<String, String> parentFilterMap = new LinkedHashMap<>() {{
        put("accommodationTypeId", "accommodationTypeId.aggregateId");
    }};
}
