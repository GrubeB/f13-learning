# Audit

## Table of Contents

* [General Info](#general-information)
* [Usage](#usage)

## General Information

Jar contains classes to auditing JPA Entities in Spring application.

## Usage

### Configuration

1. Example configuration.

```java
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.app.common.audit.AuditorConfig;
import pl.app.common.audit.AuthenticationProvider;
import pl.app.common.audit.AuditHibernateListenerConfig;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
@Import({
        AuditorConfig.class,
        AuditHibernateListenerConfig.class
})
public class AuditConfig {
    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new AuthenticationProvider() {
            @Override
            public Optional<String> getCurrentUserName() {
                return Optional.of("-");
            }
        };
    }
}
```