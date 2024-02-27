package pl.app.common.search_criteria.resolver;


import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

public class BodyArgumentResolverWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new SearchCriteriaHandlerMethodBodyArgumentResolver());
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }
}
