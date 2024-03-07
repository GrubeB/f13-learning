package pl.app.authorization.permision.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.authorization.permision.query.dto.PermissionDto;
import pl.app.authorization.permision.query.model.PermissionQuery;
import pl.app.common.ddd.AggregateId;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.shared.dto.BaseDto;

@Getter
@Component
@RequiredArgsConstructor
public class PermissionQueryMapper extends BaseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        addMapper(PermissionQuery.class, PermissionDto.class, e -> modelMapper.map(e, PermissionDto.class));
        addMapper(PermissionQuery.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(PermissionQuery.class, AggregateId.class, e -> new AggregateId(e.getId()));
    }
}
