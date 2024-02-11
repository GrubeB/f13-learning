package pl.app.property.accommodation_availability.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.dto_criteria.Dto;
import pl.app.common.shared.dto.BaseDto;
import pl.app.property.accommodation_availability.adapter.out.persistence.AccommodationTypeAvailabilityEntity;
import pl.app.property.accommodation_availability.adapter.out.persistence.AccommodationTypeAvailabilityEntityRepository;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationAvailabilityException;
import pl.app.property.accommodation_availability.query.dto.AccommodationTypeAvailabilityDto;
import pl.app.property.accommodation_availability.query.mapper.AccommodationTypeAvailabilityQueryMapper;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Getter
class AccommodationTypeAvailabilityQueryServiceImpl implements
        AccommodationTypeAvailabilityQueryService {
    private final AccommodationTypeAvailabilityEntityRepository repository;
    private final AccommodationTypeAvailabilityEntityRepository specificationRepository;
    private final AccommodationTypeAvailabilityQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("AccommodationTypeAvailabilityDto", AccommodationTypeAvailabilityDto.class);
        put("BaseDto", BaseDto.class);
    }};

    @Override
    public <T> T fetchByAccommodationTypeId(UUID accommodationTypeId, Dto dto) {
        AccommodationTypeAvailabilityEntity accommodationTypeAvailabilityEntity = repository
                .findIdByAccommodationType_AccommodationTypeId(accommodationTypeId)
                .map(repository::findById)
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new)
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
        return mapper.map(accommodationTypeAvailabilityEntity, getClass(dto));
    }
}
