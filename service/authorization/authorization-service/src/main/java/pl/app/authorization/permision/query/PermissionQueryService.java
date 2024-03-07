package pl.app.authorization.permision.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.authorization.permision.query.dto.PermissionDto;
import pl.app.authorization.permision.query.model.PermissionQuery;
import pl.app.common.ddd.AggregateId;
import pl.app.common.service.QueryService;
import pl.app.common.shared.dto.BaseDto;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
public class PermissionQueryService implements
        QueryService.Full<UUID, PermissionQuery> {
    private final PermissionQueryRepository repository;
    private final PermissionQueryRepository specificationRepository;
    private final PermissionQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("PermissionDto", PermissionDto.class);
        put("BaseDto", BaseDto.class);
        put("AggregateId", AggregateId.class);
    }};
}
