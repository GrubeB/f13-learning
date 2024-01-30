package pl.app.property.accommodation_type.adapter.out.persistence.mapper;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.mapper.Mapper;
import pl.app.common.mapper.Merger;
import pl.app.ddd.AggregateId;
import pl.app.property.accommodation_type.adapter.out.persistence.model.AccommodationEntity;
import pl.app.property.accommodation_type.adapter.out.persistence.model.AccommodationTypeEntity;
import pl.app.property.accommodation_type.application.domain.Accommodation;
import pl.app.property.accommodation_type.application.domain.AccommodationType;
import pl.app.property.property.model.PropertyEntity;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Getter
public class AccommodationMapper implements
        Mapper,
        Merger {
    private final Map<AbstractMap.SimpleEntry<Class<?>, Class<?>>, Function<?, ?>> mappers = new HashMap<>();
    private final Map<Class<?>, BiFunction<?, ?, ?>> mergers = new HashMap<>();

    @PostConstruct
    void init() {
        addMapper(AccommodationTypeEntity.class, AccommodationType.class, this::mapToAccommodationType);
        addMapper(AccommodationType.class, AccommodationTypeEntity.class, this::mapToAccommodationTypeEntity);
        addMerger(AccommodationTypeEntity.class, this::mergeAccommodationTypeEntity);
    }

    private AccommodationTypeEntity mergeAccommodationTypeEntity(AccommodationTypeEntity target, AccommodationTypeEntity source) {
        if (target == null) {
            return target;
        }
        if (source.getAccommodationTypeDetails() != null) {
            target.setAccommodationTypeDetails(source.getAccommodationTypeDetails());
        }
        if (source.getAccommodations() != null) {
            target.setAccommodations(source.getAccommodations());
        }
        if (source.getProperty() != null) {
            target.setProperty(source.getProperty());
        }
        return target;
    }

    private AccommodationTypeEntity mapToAccommodationTypeEntity(AccommodationType domain) {
        AccommodationTypeEntity entity = AccommodationTypeEntity.builder()
                .accommodationTypeId(domain.getAggregateId().getId())
                .accommodations(domain.getAccommodations().stream().map(this::mapToAccommodationEntity).collect(Collectors.toSet()))
                .property(PropertyEntity.builder().propertyId(domain.getPropertyId().getId()).build())
                .build();
        entity.getAccommodations().forEach(acc -> acc.setAccommodationType(entity));
        return entity;
    }

    private AccommodationEntity mapToAccommodationEntity(Accommodation domain) {
        return AccommodationEntity.builder()
                .accommodationId(domain.getId())
                .name(domain.getName())
                .description(domain.getDescription())
                .build();
    }

    private Accommodation mapToAccommodation(AccommodationEntity entity) {
        return new Accommodation(
                entity.getAccommodationId(),
                entity.getName(),
                entity.getDescription()
        );
    }

    private AccommodationType mapToAccommodationType(AccommodationTypeEntity entity) {
        return new AccommodationType(
                new AggregateId(entity.getId()),
                entity.getAccommodations().stream().map(this::mapToAccommodation).collect(Collectors.toList()),
                new AggregateId(entity.getProperty().getId())
        );
    }
}
