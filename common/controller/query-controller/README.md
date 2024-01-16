# Query Controller

## Table of Contents

* [General Info](#general-information)
* [Usage](#usage)

## General Information

Jar contains interfaces to fast create query controllers.

## Usage

1. Defining QueryController.

```java
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.query_controller.QueryController;
import pl.app.learning.organization.model.OrganizationEntity;
import pl.app.learning.organization.service.OrganizationQueryService;

import java.util.UUID;

@RestController
@RequestMapping(OrganizationQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class OrganizationQueryController implements
        QueryController.DtoFetchable.Full<UUID, OrganizationEntity> {
    public static final String resourceName = "organizations";
    public static final String resourcePath = "/api/v1/" + resourceName;

    public final OrganizationQueryService service;
}
```