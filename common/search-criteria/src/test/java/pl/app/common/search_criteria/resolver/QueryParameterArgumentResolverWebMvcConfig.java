package pl.app.common.search_criteria.resolver;


import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

public class QueryParameterArgumentResolverWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new SearchCriteriaHandlerMethodQueryParameterArgumentResolver());
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }
}
