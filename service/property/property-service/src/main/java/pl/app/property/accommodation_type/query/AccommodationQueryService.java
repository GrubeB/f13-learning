package pl.app.property.accommodation_type.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.service.QueryService;
import pl.app.common.shared.dto.BaseDto;
import pl.app.property.accommodation_type.query.dto.AccommodationDto;
import pl.app.property.accommodation_type.query.model.AccommodationQuery;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
public class AccommodationQueryService implements
        QueryService.Full<UUID, AccommodationQuery> {
    private final AccommodationQueryRepository repository;
    private final AccommodationQueryRepository specificationRepository;
    private final AccommodationQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("AccommodationDto", AccommodationDto.class);
        put("BaseDto", BaseDto.class);
        put("AggregateId", AggregateId.class);
    }};
}
