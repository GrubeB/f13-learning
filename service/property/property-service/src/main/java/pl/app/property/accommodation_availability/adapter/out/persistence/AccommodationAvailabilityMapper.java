package pl.app.property.accommodation_availability.adapter.out.persistence;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.mapper.Join;
import pl.app.common.mapper.MergerUtils;
import pl.app.ddd.AggregateId;
import pl.app.ddd.event.DomainEventPublisher;
import pl.app.ddd.shared.DateRange;
import pl.app.property.accommodation_availability.application.domain.model.*;
import pl.app.property.accommodation_type.adapter.out.persistence.AccommodationEntity;
import pl.app.property.accommodation_type.adapter.out.persistence.AccommodationTypeEntity;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Getter
public class AccommodationAvailabilityMapper extends BaseMapper {

    private final DomainEventPublisher domainEventPublisher;
    private final AccommodationAssignmentPolicy accommodationAssignmentPolicy;
    private final AccommodationTypeAvailabilityPolicy accommodationTypeAvailabilityPolicy;

    @PostConstruct
    void init() {
        addMapper(AccommodationTypeAvailability.class, AccommodationTypeAvailabilityEntity.class, this::mapToAccommodationTypeAvailabilityEntity);
        addMapper(AccommodationTypeAvailabilityEntity.class, AccommodationTypeAvailability.class, this::mapToAccommodationTypeAvailability);
        addMerger(AccommodationTypeAvailabilityEntity.class, this::mergeAccommodationTypeAvailabilityEntity);
    }

    // TO DOMAIN
    private AccommodationTypeAvailability mapToAccommodationTypeAvailability(AccommodationTypeAvailabilityEntity entity) {
        List<Accommodation> accommodations = mapAccommodations(entity);
        List<AccommodationTypeReservation> typeReservations = mapAccommodationTypeReservations(entity, accommodations);
        return new AccommodationTypeAvailability(
                new AggregateId(entity.getId()),
                domainEventPublisher,
                new AggregateId(entity.getAccommodationType().getProperty().getPropertyId()),
                new AggregateId(entity.getAccommodationType().getAccommodationTypeId()),
                accommodations,
                typeReservations
        );
    }

    private List<Accommodation> mapAccommodations(AccommodationTypeAvailabilityEntity entity) {
        final Set<AccommodationEntity> accommodations = entity.getAccommodationType().getAccommodations();
        final Set<AccommodationRestrictionEntity> accommodationRestrictions = entity.getAccommodationRestrictions();
        return accommodations.stream()
                .map(accommodationEntity -> {
                    List<AccommodationRestrictionEntity> restrictions = accommodationRestrictions.stream()
                            .filter(restrictionEntity -> Objects.equals(accommodationEntity.getId(), restrictionEntity.getAccommodation().getId()))
                            .toList();
                    return new Accommodation(accommodationEntity.getId(), restrictions.stream().map(this::mapToAccommodationRestriction).collect(Collectors.toList()));
                }).collect(Collectors.toList());
    }

    private AccommodationRestriction mapToAccommodationRestriction(AccommodationRestrictionEntity entity) {
        return new AccommodationRestriction(
                entity.getId(),
                entity.getStatus(),
                new DateRange<>(entity.getStartDate(), entity.getEndDate())
        );
    }

    private List<AccommodationTypeReservation> mapAccommodationTypeReservations(AccommodationTypeAvailabilityEntity entity, List<Accommodation> accommodations) {

        return entity.getAccommodationTypeReservations().stream()
                .map(typeReservation -> {
                    List<AccommodationTypeReservationItem> typeReservationItems = typeReservation.getAccommodationRestrictions().stream()
                            .map(restriction -> {
                                Accommodation accommodation = accommodations.stream().filter(acc -> Objects.equals(acc.getId(), restriction.getAccommodation().getId())).findAny().get();
                                AccommodationRestriction restrictionDomain = accommodation.getRestrictionById(restriction.getId()).get();
                                return new AccommodationTypeReservationItem(accommodation, restrictionDomain);
                            }).collect(Collectors.toList());
                    return new AccommodationTypeReservation(
                            typeReservation.getId(),
                            new DateRange<>(typeReservation.getStartDate(), typeReservation.getEndDate()),
                            typeReservation.getStatus(),
                            typeReservationItems
                    );
                })
                .collect(Collectors.toList());
    }

    // TO ENTITY

    private AccommodationTypeAvailabilityEntity mapToAccommodationTypeAvailabilityEntity(AccommodationTypeAvailability domain) {
        AccommodationTypeEntity accommodationType = AccommodationTypeEntity.builder().accommodationTypeId(domain.getAccommodationTypeId().getId()).build();
        Set<AccommodationEntity> allAccommodations = domain.getAccommodations().stream()
                .map(accommodationDomain -> AccommodationEntity.builder().accommodationId(accommodationDomain.getId()).build())
                .collect(Collectors.toSet());
        Set<AccommodationRestrictionEntity> allRestrictions = domain.getAccommodations().stream()
                .map(accommodationDomain ->
                        accommodationDomain.getRestrictions().stream()
                                .map(restrictionDomain -> AccommodationRestrictionEntity.builder()
                                        .accommodationRestrictionId(restrictionDomain.getId())
                                        .status(restrictionDomain.getStatus())
                                        .startDate(restrictionDomain.getDateRange().getFromDate())
                                        .endDate(restrictionDomain.getDateRange().getToDate())
                                        .accommodation(allAccommodations.stream().filter(acc -> Objects.equals(acc.getId(), accommodationDomain.getId())).findAny().get())
                                        .build())
                                .collect(Collectors.toSet())
                ).flatMap(Set::stream)
                .collect(Collectors.toSet());

        Set<AccommodationTypeReservationEntity> accommodationTypeReservations = domain.getTypeReservations().stream()
                .map(typeReservationDomain -> {
                    Set<AccommodationRestrictionEntity> accommodationRestrictions = typeReservationDomain.getReservationItems().stream()
                            .map(reservationItemDomain -> allRestrictions.stream().filter(res -> Objects.equals(res.getId(), reservationItemDomain.getRestriction().getId())).findAny().get())
                            .collect(Collectors.toSet());
                    return AccommodationTypeReservationEntity.builder()
                            .accommodationTypeReservationId(typeReservationDomain.getId())
                            .status(typeReservationDomain.getAssignedStatus())
                            .startDate(typeReservationDomain.getDateRange().getFromDate())
                            .endDate(typeReservationDomain.getDateRange().getToDate())
                            .accommodationRestrictions(accommodationRestrictions)
                            .build();
                }).collect(Collectors.toSet());
        AccommodationTypeAvailabilityEntity entity = AccommodationTypeAvailabilityEntity.builder()
                .accommodationTypeAvailabilityId(domain.getId())
                .accommodationType(accommodationType)
                .build();
        entity.setAccommodationTypeReservations(accommodationTypeReservations);
        entity.setAccommodationRestrictions(allRestrictions);
        return entity;
    }

    private AccommodationTypeAvailabilityEntity mergeAccommodationTypeAvailabilityEntity(AccommodationTypeAvailabilityEntity target, AccommodationTypeAvailabilityEntity source) {
        if (target == null) {
            return target;
        }

        if (source.getAccommodationType() != null) {
            target.setAccommodationType(source.getAccommodationType());
        }
        if (source.getAccommodationRestrictions() != null) {
            Set<AccommodationRestrictionEntity> mergedAccommodationRestrictionEntities = MergerUtils.mergeCollections(Join.RIGHT,
                    target.getAccommodationRestrictions(),
                    source.getAccommodationRestrictions(),
                    this::mergeAccommodationRestrictionEntity, AccommodationRestrictionEntity::getId);
            target.setAccommodationRestrictions(mergedAccommodationRestrictionEntities);
        }
        if (source.getAccommodationTypeReservations() != null) {
            Set<AccommodationTypeReservationEntity> mergedAccommodationTypeReservationEntities = MergerUtils.mergeCollections(Join.RIGHT,
                    target.getAccommodationTypeReservations(),
                    source.getAccommodationTypeReservations(),
                    this::mergeAccommodationTypeReservationEntity, AccommodationTypeReservationEntity::getId);
            target.setAccommodationTypeReservations(mergedAccommodationTypeReservationEntities);
        }
        return target;
    }

    private AccommodationRestrictionEntity mergeAccommodationRestrictionEntity(AccommodationRestrictionEntity target, AccommodationRestrictionEntity source) {
        if (target == null) {
            return target;
        }

        if (source.getStatus() != null) {
            target.setStatus(source.getStatus());
        }
        if (source.getStartDate() != null) {
            target.setStartDate(source.getStartDate());
        }
        if (source.getEndDate() != null) {
            target.setEndDate(source.getEndDate());
        }
        if (source.getAccommodation() != null) {
            target.setAccommodation(source.getAccommodation());
        }
        if (source.getAccommodationTypeAvailability() != null) {
            target.setAccommodationTypeAvailability(source.getAccommodationTypeAvailability());
        }
        return target;
    }

    private AccommodationTypeReservationEntity mergeAccommodationTypeReservationEntity(AccommodationTypeReservationEntity target, AccommodationTypeReservationEntity source) {
        if (target == null) {
            return target;
        }

        if (source.getStatus() != null) {
            target.setStatus(source.getStatus());
        }
        if (source.getStartDate() != null) {
            target.setStartDate(source.getStartDate());
        }
        if (source.getEndDate() != null) {
            target.setEndDate(source.getEndDate());
        }
        if (source.getAccommodationRestrictions() != null) {
            Set<AccommodationRestrictionEntity> mergedAccommodationRestrictionEntity = MergerUtils.mergeCollections(Join.RIGHT,
                    target.getAccommodationRestrictions(),
                    source.getAccommodationRestrictions(),
                    this::mergeAccommodationRestrictionEntity, AccommodationRestrictionEntity::getId);
            target.setAccommodationRestrictions(mergedAccommodationRestrictionEntity);
        }
        if (source.getAccommodationTypeAvailability() != null) {
            target.setAccommodationTypeAvailability(source.getAccommodationTypeAvailability());
        }
        return target;
    }
}
