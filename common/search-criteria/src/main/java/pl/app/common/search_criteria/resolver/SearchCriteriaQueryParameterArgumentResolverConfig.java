package pl.app.common.search_criteria.resolver;

import org.springframework.context.annotation.Bean;

public class SearchCriteriaQueryParameterArgumentResolverConfig {
    @Bean
    public SearchCriteriaArgumentResolver searchCriteriaArgumentResolver() {
        return new SearchCriteriaHandlerMethodQueryParameterArgumentResolver();
    }
}
