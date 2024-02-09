package pl.app.property.accommodation_type.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.shared.dto.BaseDto;
import pl.app.property.accommodation_type.adapter.out.persistence.AccommodationTypeEntity;
import pl.app.property.accommodation_type.adapter.out.persistence.AccommodationTypeRepository;
import pl.app.property.accommodation_type.query.dto.AccommodationTypeDto;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class AccommodationTypeQueryServiceImpl implements AccommodationTypeQueryService {
    private final AccommodationTypeRepository repository;
    private final AccommodationTypeRepository specificationRepository;
    private final AccommodationTypeQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("AccommodationTypeDto", AccommodationTypeDto.class);
        put("BaseDto", BaseDto.class);
    }};
}
