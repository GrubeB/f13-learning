package pl.app.property.accommodation_type.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.ddd.AggregateId;
import pl.app.property.accommodation_type.adapter.out.persistence.mapper.AccommodationMapper;
import pl.app.property.accommodation_type.adapter.out.persistence.model.AccommodationEntity;
import pl.app.property.accommodation_type.adapter.out.persistence.model.AccommodationTypeEntity;
import pl.app.property.accommodation_type.adapter.out.persistence.repository.AccommodationTypeRepository;
import pl.app.property.accommodation_type.application.domain.Accommodation;
import pl.app.property.accommodation_type.application.domain.AccommodationType;
import pl.app.property.accommodation_type.application.domain.AccommodationTypeException;
import pl.app.property.accommodation_type.application.port.out.AccommodationTypeRepositoryPort;

import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
@RequiredArgsConstructor
class AccommodationTypePersistenceAdapter implements
        AccommodationTypeRepositoryPort {

    private final AccommodationMapper mapper;
    private final AccommodationTypeRepository repository;

    @Override
    public void save(AccommodationType aggregate) {
        Optional<AccommodationTypeEntity> existingEntity = repository.findById(aggregate.getAggregateId().getId());
        AccommodationTypeEntity mappedAggregate = mapper.map(aggregate, AccommodationTypeEntity.class);
        if (existingEntity.isPresent()) {
            AccommodationTypeEntity mergedEntity = mapper.merge(existingEntity.get(), mappedAggregate);
            repository.save(mergedEntity);
        } else {
            repository.save(mappedAggregate);
        }
    }

    @Override
    public AccommodationType load(AggregateId aggregateId) {
        AccommodationTypeEntity entity = repository.findById(aggregateId.getId())
                .orElseThrow(() -> AccommodationTypeException.NotFoundAccommodationTypeException.fromId(aggregateId.getId()));
        return mapper.map(entity, AccommodationType.class);
    }
}
