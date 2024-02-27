package pl.app.property.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.app.common.dto_criteria.resolver.DtoArgumentResolver;
import pl.app.common.dto_criteria.resolver.DtoArgumentResolverConfig;
import pl.app.common.path_variable.resolver.PathVariablesArgumentResolver;
import pl.app.common.path_variable.resolver.PathVariablesArgumentResolverConfig;
import pl.app.common.search_criteria.resolver.SearchCriteriaArgumentResolver;
import pl.app.common.search_criteria.resolver.SearchCriteriaBodyArgumentResolverConfig;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Import({
        DtoArgumentResolverConfig.class,
        SearchCriteriaBodyArgumentResolverConfig.class,
        PathVariablesArgumentResolverConfig.class
})
public class WebMvcConfig implements WebMvcConfigurer {
    private final DtoArgumentResolver dtoArgumentResolver;
    private final SearchCriteriaArgumentResolver searchCriteriaArgumentResolver;
    private final PathVariablesArgumentResolver pathVariablesArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(dtoArgumentResolver);
        resolvers.add(searchCriteriaArgumentResolver);
        resolvers.add(pathVariablesArgumentResolver);
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }
}
