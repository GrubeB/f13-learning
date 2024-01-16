# Command Controller

## Table of Contents

* [General Info](#general-information)
* [Usage](#usage)

## General Information

Jar contains interfaces to fast create command controllers for CUD operations.

## Usage

1. Defining CommandController.

```java
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.command_controller.CommandController;
import pl.app.learning.organization.dto.OrganizationDto;
import pl.app.learning.organization.model.OrganizationEntity;
import pl.app.learning.organization.service.OrganizationService;

import java.util.UUID;

@RestController
@RequestMapping(OrganizationController.resourcePath)
@RequiredArgsConstructor
@Getter
public class OrganizationController implements
        CommandController.Creatable.DtoCreatable<UUID, OrganizationEntity, OrganizationDto, OrganizationDto>,
        CommandController.Updatable.DtoUpdatable<UUID, OrganizationEntity, OrganizationDto, OrganizationDto>,
        CommandController.Deletable.SimpleDeletable<UUID, OrganizationEntity> {
    public static final String resourceName = "organizations";
    public static final String resourcePath = "/api/v1/" + resourceName;

    public final OrganizationService service;
}
```