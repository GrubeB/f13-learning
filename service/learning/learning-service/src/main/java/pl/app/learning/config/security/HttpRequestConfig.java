package pl.app.learning.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import pl.app.common.security.authorization.AuthorizeHttpRequestCustomizer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.http.HttpMethod.GET;

@Configuration
public class HttpRequestConfig {

    private static final String byId = "/{id}";
    private static final String all = "/**";

    @Bean
    AuthorizeHttpRequestCustomizer authorizeHttpRequestCustomizer() {
        return c -> c
                .requestMatchers(and(or(GET))).permitAll()
                .anyRequest().authenticated();
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
