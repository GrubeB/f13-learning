package pl.app.common.search_criteria.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import pl.app.common.search_criteria.SearchCriteria;

public interface SearchCriteriaArgumentResolver extends HandlerMethodArgumentResolver {
    @NonNull
    @Override
    SearchCriteria resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
                                   NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory);
}
