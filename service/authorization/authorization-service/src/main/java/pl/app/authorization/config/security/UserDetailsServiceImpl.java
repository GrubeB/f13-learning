package pl.app.authorization.config.security;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.app.authorization.role.query.model.RoleHasPermissionQuery;
import pl.app.authorization.role.query.model.RoleQuery;
import pl.app.authorization.user.query.UserQueryService;
import pl.app.authorization.user.query.model.UserHasRoleQuery;
import pl.app.authorization.user.query.model.UserQuery;
import pl.app.common.security.user_details.CustomUserDetails;

import java.util.List;
import java.util.Set;


@Component
class UserDetailsServiceImpl implements UserDetailsService {
    private final UserQueryService userQueryService;

    public UserDetailsServiceImpl(@Lazy UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserQuery user = userQueryService.fetchByEmail(email);
        return new CustomUserDetails(user.getId(), user.getEmail(), user.getPassword(), toGrantedAuthority(user.getRoles()));
    }

    private static List<? extends GrantedAuthority> toGrantedAuthority(Set<UserHasRoleQuery> userHasRoleQueries) {
        return userHasRoleQueries.stream()
                .map(UserHasRoleQuery::getRole)
                .map(RoleQuery::getPermissions)
                .flatMap(Set::stream)
                .map(RoleHasPermissionQuery::getPermission)
                .map(p -> new SimpleGrantedAuthority(p.getName()))
                .toList();
    }
}
