package pl.app.common.model.audit;

import java.util.Optional;

public interface UserNameProvider {
    Optional<String> getCurrentUserName();
}
