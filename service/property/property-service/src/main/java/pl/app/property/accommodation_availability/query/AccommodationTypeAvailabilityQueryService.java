package pl.app.property.accommodation_availability.query;

import pl.app.common.service.QueryService;
import pl.app.property.accommodation_availability.adapter.out.persistence.AccommodationTypeAvailabilityEntity;

import java.util.UUID;


public interface AccommodationTypeAvailabilityQueryService extends
        QueryService.Full<UUID, AccommodationTypeAvailabilityEntity> {
    AccommodationTypeAvailabilityEntity fetchByAccommodationTypeId(UUID accommodationTypeId);
}
