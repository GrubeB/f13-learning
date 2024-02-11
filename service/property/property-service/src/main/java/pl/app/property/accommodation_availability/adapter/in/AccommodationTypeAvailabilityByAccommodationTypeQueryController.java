package pl.app.property.accommodation_availability.adapter.in;

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
import pl.app.property.accommodation_availability.adapter.out.persistence.AccommodationTypeAvailabilityEntity;
import pl.app.property.accommodation_availability.query.AccommodationTypeAvailabilityQueryService;

import java.util.UUID;

@RestController
@RequestMapping(AccommodationTypeAvailabilityByAccommodationTypeQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class AccommodationTypeAvailabilityByAccommodationTypeQueryController {

    public static final String resourceName = "accommodation-type-availabilities";
    public static final String resourcePath = "/api/v1/accommodation-types/{accommodationTypeId}/" + resourceName ;

    private final AccommodationTypeAvailabilityQueryService service;

    @GetMapping
    ResponseEntity<?> fetchByIdAndDto(@PathVariable UUID accommodationTypeId, Dto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getService().fetchByAccommodationTypeId(accommodationTypeId, dto));
    }

}
