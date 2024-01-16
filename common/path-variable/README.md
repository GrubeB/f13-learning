# Path variable

## Table of Contents

* [General Info](#general-information)
* [Usage](#usage)

## General Information

Purpose of jar is to provide class to hold path variables from request.

## Usage

### Configuration

1. Adding [PathVariablesArgumentResolver.java](src%2Fmain%2Fjava%2Fpl%2Fapp%2Fcommon%2Fpath_variable%2Fresolver%2FPathVariablesArgumentResolver.java) to Spring's resolvers.

```java
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.app.common.path_variable.resolver.PathVariablesArgumentResolver;
import pl.app.common.path_variable.resolver.PathVariablesArgumentResolverConfig;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Import({
        PathVariablesArgumentResolverConfig.class
})
public class WebMvcConfig implements WebMvcConfigurer {
    private final PathVariablesArgumentResolver pathVariablesArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(pathVariablesArgumentResolver);
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }
}
```

### Usage in controller

1. Defining handler method.

```java
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.path_variable.PathVariables;
import pl.app.learning.organization.model.OrganizationEntity;
import pl.app.learning.organization.service.OrganizationQueryService;

import java.util.UUID;

@RestController
@RequestMapping(OrganizationQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class OrganizationQueryController {
   public static final String resourceName = "organizations";
   public static final String resourcePath = "/api/v1/" + resourceName;

   public final OrganizationQueryService service;

   @GetMapping("/{id}")
   public ResponseEntity<OrganizationEntity> fetchByIdAndDto(UUID uuid, PathVariables pathVariables) {
       // ...
       pathVariables.getVariables();
       // ...
   }
}
```