package pl.app.common.controller.support;



import pl.app.common.path_variable.PathVariables;
import pl.app.common.search_criteria.Operator;
import pl.app.common.search_criteria.SearchCriteria;
import pl.app.common.search_criteria.SearchCriteriaItem;

import java.util.Map;
import java.util.UUID;

public class ControllerSupport {
    public interface FilterSupport {
        default void filterByParentIds(SearchCriteria searchCriteria, PathVariables pathVariables, Map<String, String> parentFilterMap) {
            parentFilterMap.forEach((key, value) -> {
                String variableValue = pathVariables.getVariables().get(key);
                if (variableValue != null) {
                    searchCriteria.addCriteria(new SearchCriteriaItem(value, Operator.EQUAL, variableValue));
                }
            });
        }

        Map<String, String> getParentFilterMap();
    }

    public interface ParentIdResolverSupport<ID> {
        @SuppressWarnings("unchecked")
        default ID getParentId(PathVariables pathVariables) {
            try {
                if (getParentIdPathVariableName() != null) {
                    String parentIdPathVariableName = getParentIdPathVariableName();
                    return (ID) UUID.fromString(pathVariables.get(parentIdPathVariableName));
                }
                throw new RuntimeException("parent id could not be extracted from path");
            } catch (Exception e) {
                throw new RuntimeException("parent id could not be extracted from path");
            }
        }

        String getParentIdPathVariableName();
    }
}
