# DTO criteria

## Table of Contents

* [General Info](#general-information)
* [Usage](#usage)

## General Information

Purpose of jar is to provide class to hold DTO name. The DTO object is passed to the service, and result can be mapped to the appropriate class.

## Usage

### Configuration

1. Adding [DtoArgumentResolver.java](src%2Fmain%2Fjava%2Fpl%2Fapp%2Fcommon%2Fdto_criteria%2Fresolver%2FDtoArgumentResolver.java) to Spring's resolvers.

```java
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.app.common.dto_criteria.resolver.DtoArgumentResolver;
import pl.app.common.dto_criteria.resolver.DtoArgumentResolverConfig;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Import({
        DtoArgumentResolverConfig.class
})
public class WebMvcConfig implements WebMvcConfigurer {
   private final DtoArgumentResolver dtoArgumentResolver;

   @Override
   public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
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
   public ResponseEntity<?> fetchByIdAndDto(UUID uuid, Dto dto) {
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(service.fetchById(id, dto));
   }
}
```
2. Send request

```
curl --location 'http://localhost:9006/api/v1/organizations/55b2700c-af3d-4ddc-85c6-06a56849229e?dto=BaseDto'
```