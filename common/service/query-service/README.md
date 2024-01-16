# Query service

## Table of Contents

* [General Info](#general-information)
* [Usage](#usage)

## General Information

Jar contains interfaces to fast create query services.

## Usage

1. Defining QueryService.

```java
import pl.app.common.service.QueryService;
import pl.app.learning.organization.model.OrganizationEntity;

import java.util.UUID;

public interface OrganizationQueryService extends
        QueryService.Full<UUID, OrganizationEntity> {
}
```


2. Defining implementation of QueryService.

```java
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.organization.dto.OrganizationDto;
import pl.app.learning.organization.mapper.OrganizationMapper;
import pl.app.learning.organization.persistence.OrganizationRepository;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class OrganizationQueryServiceImpl implements
        OrganizationQueryService {
 private final OrganizationRepository repository;
 private final OrganizationRepository specificationRepository;
 private final OrganizationMapper mapper;

 private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
  put("OrganizationDto", OrganizationDto.class);
  put("BaseDto", BaseDto.class);
 }};
}
```