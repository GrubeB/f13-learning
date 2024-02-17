package pl.app.common.model.audit;

import java.util.Optional;

public interface AuthenticationProvider {
    Optional<String> getCurrentUserName();
}
