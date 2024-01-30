package pl.app.property.accommodation_type.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.shared.dto.BaseDto;
import pl.app.property.accommodation_type.adapter.out.persistence.repository.AccommodationTypeRepository;
import pl.app.property.accommodation_type.query.dto.AccommodationTypeDto;
import pl.app.property.accommodation_type.query.mapper.AccommodationTypeQueryMapper;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class AccommodationQueryServiceImpl implements AccommodationQueryService {
    private final AccommodationTypeRepository repository;
    private final AccommodationTypeRepository specificationRepository;
    private final AccommodationTypeQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("AccommodationTypeDto", AccommodationTypeDto.class);
        put("BaseDto", BaseDto.class);
    }};
}
