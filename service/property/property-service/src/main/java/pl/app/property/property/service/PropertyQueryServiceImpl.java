package pl.app.property.property.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.shared.dto.BaseDto;
import pl.app.property.organization.dto.OrganizationDto;
import pl.app.property.organization.mapper.OrganizationMapper;
import pl.app.property.organization.persistence.OrganizationRepository;
import pl.app.property.property.persistence.PropertyRepository;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class PropertyQueryServiceImpl implements
        PropertyQueryService {
    private final PropertyRepository repository;
    private final PropertyRepository specificationRepository;
}
