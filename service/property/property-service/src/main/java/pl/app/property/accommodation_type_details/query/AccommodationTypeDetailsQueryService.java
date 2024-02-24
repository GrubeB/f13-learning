package pl.app.property.accommodation_type_details.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.service.QueryService;
import pl.app.common.shared.dto.BaseDto;
import pl.app.property.accommodation_type_details.application.domain.AccommodationTypeDetails;
import pl.app.property.accommodation_type_details.application.port.out.AccommodationTypeDetailsRepository;
import pl.app.property.accommodation_type_details.query.dto.AccommodationTypeDetailsDto;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
public class AccommodationTypeDetailsQueryService implements
        QueryService.Full<UUID, AccommodationTypeDetails> {
    private final AccommodationTypeDetailsRepository repository;
    private final AccommodationTypeDetailsRepository specificationRepository;
    private final AccommodationTypeDetailsMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("AccommodationTypeDto", AccommodationTypeDetailsDto.class);
        put("BaseDto", BaseDto.class);
        put("AggregateId", AggregateId.class);
    }};
}
