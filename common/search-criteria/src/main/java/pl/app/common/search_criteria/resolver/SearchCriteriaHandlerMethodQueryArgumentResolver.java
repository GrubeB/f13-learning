package pl.app.common.search_criteria.resolver;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import pl.app.common.search_criteria.ConditionOperator;
import pl.app.common.search_criteria.Operator;
import pl.app.common.search_criteria.SearchCriteria;
import pl.app.common.search_criteria.SearchCriteriaItem;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
        return new SearchCriteria(resolveSearchCriteriaItems(queryParam));
    }

    public List<SearchCriteriaItem> resolveSearchCriteriaItems(String query) {
        LinkedList<SearchCriteriaItem> items = new LinkedList<>();
        String[] conjunction = query.split("(?<=\\sAND|and\\s)(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        for (String conjunctionElement : conjunction) {
            String[] disjunction = conjunctionElement.split("(?<=\\sOR|or\\s)(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
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
            ConditionOperator conditionOperator = resolveConditionOperator(value);
            value = removeConditionOperator(value);
            if (valueIsArray(value)) {
                value = removeBrackets(value);
                List<String> values = Stream.of(value.split(","))
                        .map(String::trim)
                        .map(this::removeQuote)
                        .toList();
                return Optional.of(new SearchCriteriaItem(split[0], operator, values, conditionOperator));
            }
            value = removeQuote(value);
            return Optional.of(new SearchCriteriaItem(split[0], operator, value, conditionOperator));
        }
        return Optional.empty();
    }

    private String removeQuote(String value) {
        return value.trim().replaceAll("^\"|\"$", "");
    }

    private String removeBrackets(String value) {
        return value.trim().replaceAll("^\\[|\\]$", "");
    }

    private String removeConditionOperator(String value) {
        return value.split("\\sAND|and\\s(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")[0]
                .split("\\sOR|or\\s(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")[0];
    }

    private ConditionOperator resolveConditionOperator(String value) {
        if (value.split("\\sAND|and\\s(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)").length == 2) {
            return ConditionOperator.AND;
        } else if (value.split("\\sOR|or\\s(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)").length == 2) {
            return ConditionOperator.OR;
        } else {
            return null;
        }
    }

    private boolean valueIsArray(String value) {
        return value.startsWith("[") && value.endsWith("]");
    }
}
