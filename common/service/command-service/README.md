# Command Service

## Table of Contents

* [General Info](#general-information)
* [Usage](#usage)

## General Information

Jar contains interfaces to fast create command services for CUD operations.

## Usage

1. Defining CommandService.

```java
import pl.app.common.service.CommandService;
import pl.app.learning.organization.dto.OrganizationDto;
import pl.app.learning.organization.model.OrganizationEntity;

import java.util.UUID;

public interface OrganizationService extends
        CommandService.Creatable.DtoCreatable<UUID, OrganizationEntity, OrganizationDto, OrganizationDto>,
        CommandService.Updatable.DtoUpdatable<UUID, OrganizationEntity, OrganizationDto, OrganizationDto>,
        CommandService.Deletable.SimpleDeletable<UUID, OrganizationEntity> {
}
```


2. Defining implementation of CommandService.

```java
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.learning.organization.mapper.OrganizationMapper;
import pl.app.learning.organization.persistence.OrganizationRepository;

@Service
@RequiredArgsConstructor
@Transactional
@Getter
class OrganizationServiceImpl implements
        OrganizationService {
    private final OrganizationRepository repository;
    private final OrganizationMapper mapper;
}
```