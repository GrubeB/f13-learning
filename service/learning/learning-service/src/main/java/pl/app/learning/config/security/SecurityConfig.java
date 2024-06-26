package pl.app.learning.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import pl.app.common.security.authentication.AuthenticationConfig;
import pl.app.common.security.token.TokenConfig;

@Configuration
@RequiredArgsConstructor
@Import({
        TokenConfig.class,
        AuthenticationConfig.class
})
public class SecurityConfig {
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
