package pl.app.authorization.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll()
        );
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.sessionManagement((sessionManagementCustomizer) ->
                sessionManagementCustomizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        return httpSecurity.build();
    }
}
