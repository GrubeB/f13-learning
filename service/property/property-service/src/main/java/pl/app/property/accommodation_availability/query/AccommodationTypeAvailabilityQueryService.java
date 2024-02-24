package pl.app.property.accommodation_availability.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.service.QueryService;
import pl.app.common.shared.dto.BaseDto;
import pl.app.property.accommodation_availability.query.dto.AccommodationTypeAvailabilityDto;
import pl.app.property.accommodation_availability.query.model.AccommodationTypeAvailabilityQuery;
import pl.app.property.accommodation_type.query.model.AccommodationTypeQuery;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
public class AccommodationTypeAvailabilityQueryService implements
        QueryService.Full<UUID, AccommodationTypeAvailabilityQuery> {
    private final AccommodationTypeAvailabilityQueryRepository repository;
    private final AccommodationTypeAvailabilityQueryRepository specificationRepository;
    private final AccommodationTypeAvailabilityQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("AccommodationTypeAvailabilityDto", AccommodationTypeAvailabilityDto.class);
        put("BaseDto", BaseDto.class);
        put("AggregateId", AggregateId.class);
    }};
}
