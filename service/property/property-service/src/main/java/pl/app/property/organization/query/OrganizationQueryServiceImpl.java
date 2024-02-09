package pl.app.property.organization.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.shared.dto.BaseDto;
import pl.app.property.organization.query.dto.OrganizationDto;
import pl.app.property.organization.application.port.out.OrganizationRepository;

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
