package pl.app.common.search_criteria.resolver;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import pl.app.common.search_criteria.SearchCriteria;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

// Allows injecting SearchCriteria instances into controller methods.
class SearchCriteriaHandlerMethodBodyArgumentResolver implements
        SearchCriteriaArgumentResolver {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return SearchCriteria.class.equals(parameter.getParameterType());
    }

    @NonNull
    @Override
    public SearchCriteria resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        try {
            HttpServletRequest nativeRequest = webRequest.getNativeRequest(HttpServletRequest.class);
            if (Objects.nonNull(nativeRequest)) {
                String requestBody = getRequestBody(nativeRequest);
                return objectMapper.readValue(requestBody, SearchCriteria.class);
            }
            return new SearchCriteria();
        } catch (Exception e) {
            return new SearchCriteria();
        }
    }

    private String getRequestBody(HttpServletRequest httpServletRequest) throws IOException {
        return httpServletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    }
}
