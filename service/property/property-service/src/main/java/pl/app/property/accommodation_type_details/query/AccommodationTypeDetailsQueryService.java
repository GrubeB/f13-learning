package pl.app.property.accommodation_type_details.query;

import pl.app.common.service.QueryService;
import pl.app.property.accommodation_type_details.application.domain.model.AccommodationTypeDetailsEntity;

import java.util.UUID;

public interface AccommodationTypeDetailsQueryService extends
        QueryService.Full<UUID, AccommodationTypeDetailsEntity> {
}
