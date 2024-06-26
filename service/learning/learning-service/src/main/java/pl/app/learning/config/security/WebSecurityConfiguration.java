package pl.app.learning.config.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.app.common.security.authorization.AuthorizeHttpRequestCustomizer;
import pl.app.common.security.token.JwtAuthenticationFilter;

import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final List<AuthorizeHttpRequestCustomizer> customizerList;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorizationManagerRequestMatcherRegistry ->
                customizerList.forEach(c -> c.customize(authorizationManagerRequestMatcherRegistry)))
        );

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource()));
        http.headers(headersCustomizer -> headersCustomizer
                .frameOptions(frameOptionsCustomizer -> frameOptionsCustomizer.sameOrigin())
                .httpStrictTransportSecurity(HeadersConfigurer.HstsConfig::disable));
        http.sessionManagement((sessionManagementCustomizer) ->
                sessionManagementCustomizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
