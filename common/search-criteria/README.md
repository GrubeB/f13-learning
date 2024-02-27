# Search Criteria

## Table of Contents

* [General Info](#general-information)
* [Usage](#usage)

## General Information

Purpose of jar is to  provide class for filtering results in query controllers.

## Usage

1. Defining handler method.

```java
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
### Configuration
There are two options, to send search criteria:
- by request body
- or query parameter

### Configuration by request body
1. Adding [SearchCriteriaArgumentResolver.java](src%2Fmain%2Fjava%2Fpl%2Fapp%2Fcommon%2Fsearch_criteria%2Fresolver%2FSearchCriteriaArgumentResolver.java) to Spring's resolvers.

```java
@Configuration
@RequiredArgsConstructor
@Import({
        SearchCriteriaBodyArgumentResolverConfig.class
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
2. Example request

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

### Configuration by query parameter
1. Adding [SearchCriteriaArgumentResolver.java](src%2Fmain%2Fjava%2Fpl%2Fapp%2Fcommon%2Fsearch_criteria%2Fresolver%2FSearchCriteriaArgumentResolver.java) to Spring's resolvers.

```java
@Configuration
@RequiredArgsConstructor
@Import({
        SearchCriteriaQueryParameterArgumentResolverConfig.class
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
2. Example request

```
curl --location 'http://localhost:9006/api/v1/organizations?query=%22name%3D%22name424%22%22' \
```
where "query=%22name%3D%22name424%22%22" is encoded "name="name424""