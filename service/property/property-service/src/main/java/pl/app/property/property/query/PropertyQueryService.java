package pl.app.property.property.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.service.QueryService;
import pl.app.common.shared.dto.BaseDto;
import pl.app.property.property.application.domain.Property;
import pl.app.property.property.application.port.out.PropertyRepository;
import pl.app.property.property.query.dto.PropertyDto;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
public class PropertyQueryService implements
        QueryService.DtoFetchable.Full<UUID, Property> {
    private final PropertyRepository repository;
    private final PropertyRepository specificationRepository;
    private final PropertyMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("PropertyDto", PropertyDto.class);
        put("BaseDto", BaseDto.class);
        put("AggregateId", AggregateId.class);
    }};
}
