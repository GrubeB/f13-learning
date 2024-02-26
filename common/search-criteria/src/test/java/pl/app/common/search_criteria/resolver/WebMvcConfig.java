package pl.app.common.search_criteria.resolver;


import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Import(SearchCriteriaArgumentResolverConfig.class)
public class WebMvcConfig implements WebMvcConfigurer {
    private final SearchCriteriaArgumentResolver searchCriteriaArgumentResolver;

    public WebMvcConfig(SearchCriteriaArgumentResolver searchCriteriaArgumentResolver) {
        this.searchCriteriaArgumentResolver = searchCriteriaArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(searchCriteriaArgumentResolver);
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }
}
