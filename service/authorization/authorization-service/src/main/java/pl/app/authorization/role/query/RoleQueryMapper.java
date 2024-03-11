package pl.app.authorization.role.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import pl.app.authorization.permision.query.model.PermissionQuery;
import pl.app.authorization.role.query.dto.RoleDto;
import pl.app.authorization.role.query.model.RoleHasPermissionQuery;
import pl.app.authorization.role.query.model.RoleQuery;
import pl.app.common.ddd.AggregateId;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.shared.dto.BaseDto;

import java.util.List;
import java.util.Set;

@Getter
@Component
@RequiredArgsConstructor
public class RoleQueryMapper extends BaseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        initRoleQueryToRoleDto();
        addMapper(RoleQuery.class, RoleDto.class, e -> modelMapper.map(e, RoleDto.class));
        addMapper(RoleQuery.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(RoleQuery.class, AggregateId.class, e -> new AggregateId(e.getId()));
    }

    private void initRoleQueryToRoleDto() {
        TypeMap<RoleQuery, RoleDto> typeMap = modelMapper.createTypeMap(RoleQuery.class, RoleDto.class);

        Converter<Set<RoleHasPermissionQuery>, List<String>> converter = context -> context.getSource().stream()
                .map(RoleHasPermissionQuery::getPermission)
                .map(PermissionQuery::getName)
                .toList();
        typeMap.addMappings(mapper -> mapper.using(converter).map(RoleQuery::getPermissions, RoleDto::setPermissions));
    }
}
