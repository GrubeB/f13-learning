package pl.app.property.accommodation_type.adapter.out.persistence;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.mapper.Join;
import pl.app.common.mapper.MergerUtils;
import pl.app.ddd.AggregateId;
import pl.app.property.accommodation_type.application.domain.Accommodation;
import pl.app.property.accommodation_type.application.domain.AccommodationType;
import pl.app.property.property.application.domain.model.PropertyEntity;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Getter
public class AccommodationMapper extends BaseMapper {
    @PostConstruct
    void init() {
        addMapper(AccommodationTypeEntity.class, AccommodationType.class, this::mapToAccommodationType);
        addMapper(AccommodationType.class, AccommodationTypeEntity.class, this::mapToAccommodationTypeEntity);
        addMerger(AccommodationTypeEntity.class, this::mergeAccommodationTypeEntity);
    }

    // TO ENTITY

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

    private AccommodationTypeEntity mergeAccommodationTypeEntity(AccommodationTypeEntity target, AccommodationTypeEntity source) {
        if (target == null) {
            return target;
        }
        if (source.getAccommodationTypeDetails() != null) {
            target.setAccommodationTypeDetails(source.getAccommodationTypeDetails());
        }
        if (source.getAccommodations() != null) {
            Set<AccommodationEntity> merged = MergerUtils.mergeCollections(Join.RIGHT_INCLUSIVE, target.getAccommodations(),
                    source.getAccommodations(), this::mergeAccommodationEntity, AccommodationEntity::getAccommodationId);
            target.setAccommodations(merged);
        }
        return target;
    }

    private AccommodationEntity mergeAccommodationEntity(AccommodationEntity target, AccommodationEntity source) {
        if (target == null) {
            return target;
        }
        if (source.getName() != null) {
            target.setName(source.getName());
        }
        if (source.getDescription() != null) {
            target.setDescription(source.getDescription());
        }
        if (source.getAccommodationType() != null) {
            target.setAccommodationType(source.getAccommodationType());
        }
        return target;
    }

    // TO DOMAIN
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
