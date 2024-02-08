package pl.app.property.accommodation_type.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.ddd.AggregateId;
import pl.app.property.accommodation_type.adapter.out.persistence.AccommodationMapper;
import pl.app.property.accommodation_type.adapter.out.persistence.AccommodationTypeEntity;
import pl.app.property.accommodation_type.adapter.out.persistence.AccommodationTypeRepository;
import pl.app.property.accommodation_type.application.domain.AccommodationType;
import pl.app.property.accommodation_type.application.domain.AccommodationTypeException;
import pl.app.property.accommodation_type.application.port.out.AccommodationTypeRepositoryPort;

import java.util.Optional;

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
