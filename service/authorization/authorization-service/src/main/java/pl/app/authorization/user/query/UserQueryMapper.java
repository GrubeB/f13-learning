package pl.app.authorization.user.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import pl.app.authorization.permision.query.model.PermissionQuery;
import pl.app.authorization.role.query.model.RoleHasPermissionQuery;
import pl.app.authorization.role.query.model.RoleQuery;
import pl.app.authorization.user.query.dto.UserDto;
import pl.app.authorization.user.query.model.UserHasRoleQuery;
import pl.app.authorization.user.query.model.UserQuery;
import pl.app.common.ddd.AggregateId;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.shared.dto.BaseDto;

import java.util.List;
import java.util.Set;

@Getter
@Component
@RequiredArgsConstructor
public class UserQueryMapper extends BaseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        initRoleQueryToRoleDto();
        addMapper(UserQuery.class, UserDto.class, e -> modelMapper.map(e, UserDto.class));
        addMapper(UserQuery.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(UserQuery.class, AggregateId.class, e -> new AggregateId(e.getId()));
    }

    private void initRoleQueryToRoleDto() {
        TypeMap<UserQuery, UserDto> typeMap = modelMapper.createTypeMap(UserQuery.class, UserDto.class);

        Converter<Set<UserHasRoleQuery>, List<String>> converter = context -> context.getSource().stream()
                .map(UserHasRoleQuery::getRole)
                .map(RoleQuery::getName)
                .toList();
        typeMap.addMappings(mapper -> mapper.using(converter).map(UserQuery::getRoles, UserDto::setRoles));

        Converter<Set<UserHasRoleQuery>, List<String>> converter2 = context -> context.getSource().stream()
                .map(UserHasRoleQuery::getRole)
                .map(RoleQuery::getPermissions)
                .flatMap(Set::stream)
                .map(RoleHasPermissionQuery::getPermission)
                .map(PermissionQuery::getName)
                .toList();
        typeMap.addMappings(mapper -> mapper.using(converter2).map(UserQuery::getRoles, UserDto::setPermissions));
    }
}
