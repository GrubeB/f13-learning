package pl.app.common.path_variable.resolver;

import org.springframework.context.annotation.Bean;

public class PathVariablesArgumentResolverConfig {
    @Bean
    public PathVariablesArgumentResolver pathVariablesArgumentResolver() {
        return new PathVariablesHandlerMethodArgumentResolver();
    }
}
