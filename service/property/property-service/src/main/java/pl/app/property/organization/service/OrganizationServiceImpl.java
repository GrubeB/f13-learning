package pl.app.property.organization.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.organization.mapper.OrganizationMapper;
import pl.app.property.organization.persistence.OrganizationRepository;

@Service
@RequiredArgsConstructor
@Transactional
@Getter
class OrganizationServiceImpl implements
        OrganizationService {
    private final OrganizationRepository repository;
    private final OrganizationMapper mapper;
}
