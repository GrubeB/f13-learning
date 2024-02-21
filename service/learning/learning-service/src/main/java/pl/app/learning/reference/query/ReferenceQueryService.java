package pl.app.learning.reference.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.service.QueryService;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.reference.query.dto.ReferenceDto;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
public class ReferenceQueryService implements
        QueryService.Full<UUID, ReferenceQuery> {
    private final ReferenceQueryRepository repository;
    private final ReferenceQueryRepository specificationRepository;
    private final ReferenceQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("ReferenceDto", ReferenceDto.class);
        put("BaseDto", BaseDto.class);
    }};
}
