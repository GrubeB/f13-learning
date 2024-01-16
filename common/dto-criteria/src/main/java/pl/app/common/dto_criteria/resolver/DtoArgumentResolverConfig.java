package pl.app.common.dto_criteria.resolver;

import org.springframework.context.annotation.Bean;

public class DtoArgumentResolverConfig {
    @Bean
    public DtoArgumentResolver dtoArgumentResolver() {
        return new DtoHandlerMethodArgumentResolver();
    }
}
