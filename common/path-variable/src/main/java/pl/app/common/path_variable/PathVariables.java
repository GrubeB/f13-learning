package pl.app.common.path_variable;

import java.util.Map;

public interface PathVariables {
    Map<String, String> getVariables();

    String get(String variableName);
}
