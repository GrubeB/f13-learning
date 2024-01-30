package pl.app.property.accommodation_type.query;


import pl.app.common.service.QueryService;
import pl.app.property.accommodation_type.adapter.out.persistence.model.AccommodationEntity;
import pl.app.property.accommodation_type.adapter.out.persistence.model.AccommodationTypeEntity;

import java.util.UUID;

public interface AccommodationQueryService extends
        QueryService.Full<UUID, AccommodationTypeEntity> {
}
