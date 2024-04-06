package pl.app.learning.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import pl.app.common.security.authorization.AuthorizeHttpRequestCustomizer;
import pl.app.common.shared.permission.PermissionName;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasAuthority;

@Configuration
public class HttpRequestConfig {

    private static final String byId = "/{id}";
    private static final String all = "/**";

    @Bean
    AuthorizeHttpRequestCustomizer authorizeHttpRequestCustomizer() {
        return c -> c
                .anyRequest().authenticated();
//                // AUTH
//                .requestMatchers(
//                        AuthenticationController.resourcePath,
//                        AuthenticationController.resourcePath + all
//                ).permitAll()
//                // FILE
//                .requestMatchers(and(or(GET), or(
//                        "/favicon.ico",
//                        FileController.resourcePath,
//                        FileController.resourcePath + all
//                )))
//                .permitAll()
//                // PERMISSION
//                .requestMatchers(and(or(GET), or(
//                        PermissionQueryController.resourcePath,
//                        PermissionQueryController.resourcePath + all
//                ))).access(
//                        hasAuthority(PermissionName.PERMISSION_READ.getPermission())
//                )
//
//                .requestMatchers(and(or(POST, DELETE, PUT), or(
//                        PermissionController.resourcePath
//                ))).access(
//                        hasAuthority(PermissionName.PERMISSION_WRITE.getPermission())
//                )
//
//                .requestMatchers(and(or(POST, DELETE, PUT), or(
//                        PermissionCommandController.resourcePath
//                ))).access(
//                        hasAuthority(PermissionName.PERMISSION_WRITE.getPermission())
//                )
//                // ROLE
//                .requestMatchers(and(or(GET), or(
//                        RoleQueryController.resourcePath,
//                        RoleQueryController.resourcePath + all
//                ))).access(
//                        hasAuthority(PermissionName.ROLE_READ.getPermission())
//                )
//
//                .requestMatchers(and(or(POST, DELETE, PUT), or(
//                        RoleController.resourcePath
//                ))).access(
//                        hasAuthority(PermissionName.ROLE_WRITE.getPermission())
//                )
//
//                .requestMatchers(and(or(POST, DELETE, PUT), or(
//                        RoleCommandController.resourcePath
//                ))).access(
//                        hasAuthority(PermissionName.ROLE_WRITE.getPermission())
//                )
//
//                // USER
//                .requestMatchers(and(or(GET), or(
//                        UserQueryController.resourcePath,
//                        UserQueryController.resourcePath + all
//                ))).access(
//                        hasAuthority(PermissionName.USER_READ.getPermission())
//                )
//
//                .requestMatchers(and(or(POST), or(
//                        UserController.resourcePath
//                ))).permitAll()
//
//                .requestMatchers(and(or(POST), or(
//                        UserCommandController.resourcePath + all
//                ))).access(
//                        hasAuthority(PermissionName.USER_WRITE.getPermission())
//                )
//
//                // OTHER
//                .anyRequest().authenticated();
    }

    private RequestMatcher or(HttpMethod... methods) {
        List<RequestMatcher> methodMatchers = Stream.of(methods).
                map(AntPathRequestMatcher::antMatcher)
                .collect(Collectors.toList());
        if (methodMatchers.size() == 1) {
            return methodMatchers.get(0);
        } else {
            return new OrRequestMatcher(methodMatchers);
        }
    }

    private RequestMatcher or(String... patterns) {
        List<RequestMatcher> patternMatchers = Stream.of(patterns).
                map(AntPathRequestMatcher::antMatcher)
                .collect(Collectors.toList());
        if (patternMatchers.size() == 1) {
            return patternMatchers.get(0);
        } else {
            return new OrRequestMatcher(patternMatchers);
        }
    }

    private RequestMatcher and(RequestMatcher... requestMatchers) {
        return new AndRequestMatcher(requestMatchers);
    }
}
