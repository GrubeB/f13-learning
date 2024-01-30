package pl.app.property.accommodation_type_details.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.shared.dto.BaseDto;
import pl.app.property.accommodation_type_details.dto.AccommodationTypeDetailsDto;
import pl.app.property.accommodation_type_details.mapper.AccommodationTypeDetailsMapper;
import pl.app.property.accommodation_type_details.persistance.AccommodationTypeDetailsRepository;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class AccommodationTypeDetailsQueryServiceImpl implements
        AccommodationTypeDetailsQueryService {
    private final AccommodationTypeDetailsRepository repository;
    private final AccommodationTypeDetailsRepository specificationRepository;
    private final AccommodationTypeDetailsMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("AccommodationTypeDto", AccommodationTypeDetailsDto.class);
        put("BaseDto", BaseDto.class);
    }};
}
