package pl.app.property.property.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.property.persistence.PropertyRepository;

@Service
@RequiredArgsConstructor
@Transactional
@Getter
class PropertyServiceImpl implements
        PropertyService {
    private final PropertyRepository repository;
}
