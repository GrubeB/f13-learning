package pl.app.learning.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.app.common.model.audit.AuditHibernateListenerConfig;
import pl.app.common.model.audit.AuditorConfig;
import pl.app.common.model.audit.UserNameProvider;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
@Import({
        AuditorConfig.class,
        AuditHibernateListenerConfig.class
})
public class AuditConfig {
    @Bean
    public UserNameProvider userNameProvider() {
        return new UserNameProvider() {
            @Override
            public Optional<String> getCurrentUserName() {
                return Optional.of("-");
            }
        };
    }
}
