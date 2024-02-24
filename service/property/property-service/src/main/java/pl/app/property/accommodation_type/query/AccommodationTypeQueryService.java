package pl.app.property.accommodation_type.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.service.QueryService;
import pl.app.common.shared.dto.BaseDto;
import pl.app.property.accommodation_type.adapter.out.AccommodationTypeRepository;
import pl.app.property.accommodation_type.query.dto.AccommodationTypeDto;
import pl.app.property.accommodation_type.query.model.AccommodationTypeQuery;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
public class AccommodationTypeQueryService implements
        QueryService.Full<UUID, AccommodationTypeQuery> {
    private final AccommodationTypeQueryRepository repository;
    private final AccommodationTypeQueryRepository specificationRepository;
    private final AccommodationTypeQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("AccommodationTypeDto", AccommodationTypeDto.class);
        put("BaseDto", BaseDto.class);
        put("AggregateId", AggregateId.class);
    }};
}
