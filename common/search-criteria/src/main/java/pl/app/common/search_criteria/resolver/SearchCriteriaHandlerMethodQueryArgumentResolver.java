package pl.app.common.search_criteria.resolver;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import pl.app.common.search_criteria.Operator;
import pl.app.common.search_criteria.SearchCriteria;
import pl.app.common.search_criteria.SearchCriteriaItem;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

// Allows injecting SearchCriteria instances into controller methods.
class SearchCriteriaHandlerMethodQueryArgumentResolver implements
        SearchCriteriaArgumentResolver {
    private final static String QUERY_PARAMETER_NAME = "query";
    private final static List<Operator> supportedOperators = new LinkedList<>() {{
        add(Operator.NO_IN);
        add(Operator.LESS_THAN_OR_EQUAL);
        add(Operator.GREATER_THAN_OR_EQUAL);
        add(Operator.NOT_EQUAL);
        add(Operator.IN);
        add(Operator.EQUAL);
        add(Operator.LIKE);
        add(Operator.GREATER_THAN);
        add(Operator.LESS_THAN);
    }};
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return SearchCriteria.class.equals(parameter.getParameterType());
    }

    @NonNull
    @Override
    public SearchCriteria resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String queryParam = webRequest.getParameter(QUERY_PARAMETER_NAME);
        if (queryParam == null || queryParam.isBlank()) {
            return new SearchCriteria();
        }
        resolveSearchCriteriaItems(queryParam);
        return null;
    }

    // name="Ala" AND length>200 OR name="Ola" AND length<120
    public List<SearchCriteriaItem> resolveSearchCriteriaItems(String query) {
        LinkedList<SearchCriteriaItem> items = new LinkedList<>();
        String[] conjunction = query.split("(?i)\\sAND\\s(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        for (String conjunctionElement : conjunction) {
            String[] disjunction = conjunctionElement.split("(?i)\\sOR\\s(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            for (String disjunctionElement : disjunction) {
                resolveSearchCriteriaItem(disjunctionElement)
                        .ifPresent(items::add);
            }
        }
        return items;
    }

    public Optional<SearchCriteriaItem> resolveSearchCriteriaItem(String query) {
        if (query == null || (query = query.trim()).isBlank()) {
            return Optional.empty();
        }
        Optional<SearchCriteriaItem> searchCriteriaItem;
        for (Operator supportedOperator : supportedOperators) {
            searchCriteriaItem = resolveSearchCriteriaItem(query, supportedOperator);
            if (searchCriteriaItem.isPresent()) {
                return searchCriteriaItem;
            }
        }
        return Optional.empty();
    }

    private Optional<SearchCriteriaItem> resolveSearchCriteriaItem(String query, Operator operator) {
        String[] split = query.split(operator.getSymbol() + "(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        if (split.length == 2) {
            String value = split[1];
            if (valueIsArray(value)) {
                value = value.replaceAll("^\\[|\\]$", "");
                List<String> values = List.of(value.split(",")).stream()
                        .map(String::trim)
                        .map(e -> e.replaceAll("^\"|\"$", ""))
                        .toList();
                return Optional.of(new SearchCriteriaItem(split[0], operator, values));
            }
            value = value.trim().replaceAll("^\"|\"$", "");

            return Optional.of(new SearchCriteriaItem(split[0], operator, value));
        }
        return Optional.empty();
    }

    private boolean valueIsArray(String value) {
        return value.startsWith("[") && value.endsWith("]");
    }
}
