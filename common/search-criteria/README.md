# Search Criteria

## Table of Contents

* [General Info](#general-information)
* [Usage](#usage)

## General Information

Purpose of jar is to  provide class for filtering results in query controllers.

## Usage

### Configuration

1. Adding [SearchCriteriaArgumentResolver.java](src%2Fmain%2Fjava%2Fpl%2Fapp%2Fcommon%2Fsearch_criteria%2Fresolver%2FSearchCriteriaArgumentResolver.java) to Spring's resolvers.

```java
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.app.common.search_criteria.resolver.SearchCriteriaArgumentResolver;
import pl.app.common.search_criteria.resolver.SearchCriteriaArgumentResolverConfig;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Import({
        SearchCriteriaArgumentResolverConfig.class
})
public class WebMvcConfig implements WebMvcConfigurer {
    private final SearchCriteriaArgumentResolver searchCriteriaArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(searchCriteriaArgumentResolver);
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }
}
```

### Usage in controller

1. Defining handler method.

```java
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.search_criteria.SearchCriteria;
import pl.app.learning.organization.model.OrganizationEntity;
import pl.app.learning.organization.service.OrganizationQueryService;

@RestController
@RequestMapping(OrganizationQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class OrganizationQueryController{
    public static final String resourceName = "organizations";
    public static final String resourcePath = "/api/v1/" + resourceName;

    public final OrganizationQueryService service;

    public ResponseEntity<Page<OrganizationEntity>> fetchAllBySearchCriteriaAndPageable(@Valid SearchCriteria searchCriteria,
                                                                                        Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getService().fetchByCriteria(searchCriteria, pageable));
    }
}

```
2. Service by using [SearchCriteriaSpecification.java](src%2Fmain%2Fjava%2Fpl%2Fapp%2Fcommon%2Fsearch_criteria%2FSearchCriteriaSpecification.java) can get Specification 
 from [SearchCriteria.java](src%2Fmain%2Fjava%2Fpl%2Fapp%2Fcommon%2Fsearch_criteria%2FSearchCriteria.java) object.

```java
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.search_criteria.SearchCriteria;
import pl.app.common.search_criteria.SearchCriteriaSpecification;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.organization.dto.OrganizationDto;
import pl.app.learning.organization.mapper.OrganizationMapper;
import pl.app.learning.organization.model.OrganizationEntity;
import pl.app.learning.organization.persistence.OrganizationRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class OrganizationQueryServiceImpl{
    private final OrganizationRepository repository;
    
    @Override
    public List<OrganizationEntity> fetchByCriteria(SearchCriteria criteria) {
        Specification<OrganizationEntity> specification = new SearchCriteriaSpecification<>(criteria);
        return repository.findAll(specification);
    }

    @Override
    public Page<OrganizationEntity> fetchByCriteria(SearchCriteria criteria, Pageable pageable) {
        Specification<OrganizationEntity> specification = new SearchCriteriaSpecification<>(criteria);
        return repository.findAll(specification, pageable);
    }
}
```
3. Example request

```
curl --location --request GET 'http://localhost:9006/api/v1/organizations?sort=name%2CASC&sort=organizationId%2CASC' \
--header 'Content-Type: application/json' \
--data '{
    "criteria": [
        {
            "field": "name",
            "operator": "LIKE",
            "value": "na",
            "valueTo": "",
            "values": []
        }
    ]
}'
```