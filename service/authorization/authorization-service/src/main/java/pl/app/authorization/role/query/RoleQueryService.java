package pl.app.authorization.role.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.authorization.role.query.dto.RoleDto;
import pl.app.authorization.role.query.model.RoleQuery;
import pl.app.common.ddd.AggregateId;
import pl.app.common.dto_criteria.Dto;
import pl.app.common.service.QueryService;
import pl.app.common.shared.dto.BaseDto;
import pl.app.common.shared.exception.NotFoundException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
public class RoleQueryService implements
        QueryService.Full<UUID, RoleQuery> {
    private final RoleQueryRepository repository;
    private final RoleQueryRepository specificationRepository;
    private final RoleQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("RoleDto", RoleDto.class);
        put("BaseDto", BaseDto.class);
        put("AggregateId", AggregateId.class);
    }};
    public <T> T fetchByName(String name, Dto dto) {
        var entity = getRepository().findByName(name)
                .orElseThrow(() -> new NotFoundException("object not found with name: " + name));
        return getMapper().map(entity, getClass(dto));
    }
}
