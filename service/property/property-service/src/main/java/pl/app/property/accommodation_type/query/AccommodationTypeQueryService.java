package pl.app.property.accommodation_type.query;


import pl.app.common.service.QueryService;
import pl.app.property.accommodation_type.adapter.out.persistence.AccommodationTypeEntity;

import java.util.UUID;

public interface AccommodationTypeQueryService extends
        QueryService.Full<UUID, AccommodationTypeEntity> {
}
