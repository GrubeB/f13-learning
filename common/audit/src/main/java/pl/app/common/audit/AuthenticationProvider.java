package pl.app.common.audit;

import java.util.Optional;

public interface AuthenticationProvider {
    Optional<String> getCurrentUserName();
}
