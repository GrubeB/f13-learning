package pl.app.common.search_criteria.resolver;

import org.springframework.context.annotation.Bean;

public class SearchCriteriaBodyArgumentResolverConfig {
    @Bean
    public SearchCriteriaArgumentResolver searchCriteriaArgumentResolver() {
        return new SearchCriteriaHandlerMethodBodyArgumentResolver();
    }
}
