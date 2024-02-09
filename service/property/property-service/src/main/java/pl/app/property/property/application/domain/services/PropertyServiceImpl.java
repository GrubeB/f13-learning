package pl.app.property.property.application.domain.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.property.application.port.in.PropertyService;
import pl.app.property.property.application.port.out.PropertyRepository;

@Service
@RequiredArgsConstructor
@Transactional
@Getter
class PropertyServiceImpl implements
        PropertyService {
    private final PropertyRepository repository;
}
