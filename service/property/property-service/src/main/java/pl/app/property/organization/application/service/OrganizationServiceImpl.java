package pl.app.property.organization.application.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.organization.application.port.in.OrganizationService;
import pl.app.property.organization.application.port.out.OrganizationRepository;
import pl.app.property.organization.query.OrganizationMapper;

@Service
@RequiredArgsConstructor
@Transactional
@Getter
class OrganizationServiceImpl implements
        OrganizationService {
    private final OrganizationRepository repository;
    private final OrganizationMapper mapper;
    private final OrganizationMapper merger;
}
