package pl.app.authorization.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.app.common.model.audit.AuditHibernateListenerConfig;
import pl.app.common.model.audit.AuditorConfig;
import pl.app.common.model.audit.UserNameProvider;
import pl.app.common.security.authentication.AuthenticationService;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
@Import({
        AuditorConfig.class,
        AuditHibernateListenerConfig.class
})
public class AuditConfig {
    @Bean
    public UserNameProvider userNameProvider(AuthenticationService authenticationService) {
        return new UserNameProvider() {
            @Override
            public Optional<String> getCurrentUserName() {
                return authenticationService.getCurrentUserName();
            }
        };
    }
}
