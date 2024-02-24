package pl.app.property.organization.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.service.QueryService;
import pl.app.common.shared.dto.BaseDto;
import pl.app.property.organization.application.domain.Organization;
import pl.app.property.organization.query.dto.OrganizationDto;
import pl.app.property.organization.application.port.out.OrganizationRepository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
public class OrganizationQueryService implements
        QueryService.Full<UUID, Organization> {
    private final OrganizationRepository repository;
    private final OrganizationRepository specificationRepository;
    private final OrganizationMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("OrganizationDto", OrganizationDto.class);
        put("BaseDto", BaseDto.class);
    }};
}
