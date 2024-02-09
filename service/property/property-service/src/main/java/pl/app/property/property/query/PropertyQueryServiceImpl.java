package pl.app.property.property.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.property.application.port.out.PropertyRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class PropertyQueryServiceImpl implements
        PropertyQueryService {
    private final PropertyRepository repository;
    private final PropertyRepository specificationRepository;
}
