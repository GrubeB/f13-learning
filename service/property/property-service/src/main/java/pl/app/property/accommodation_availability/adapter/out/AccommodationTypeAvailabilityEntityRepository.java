package pl.app.property.accommodation_availability.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.app.common.ddd.AggregateId;
import pl.app.property.accommodation_availability.application.domain.AccommodationTypeAvailability;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccommodationTypeAvailabilityEntityRepository extends
        JpaRepository<AccommodationTypeAvailability, AggregateId> {

    @Query("""
            select a.aggregateId
            from AccommodationTypeAvailability a
            where a.accommodationType = ?1""")
    Optional<AggregateId> findIdByAccommodationType_AccommodationType(AggregateId accommodationType);

    @Query("""
            select a.aggregateId
            from AccommodationTypeAvailability a inner join a.accommodationAvailabilities accommodationAvailabilities
            where accommodationAvailabilities.accommodation = ?1""")
    Optional<AggregateId> findIdByAccommodationAvailabilities_Accommodation(AggregateId accommodation);

    @Query("""
            select a.aggregateId
            from AccommodationTypeAvailability a inner join a.typeReservations typeReservations
            where typeReservations.aggregateId = ?1""")
    Optional<AggregateId> findIdByTypeReservations(AggregateId typeReservation);


    @Query("""
            select a.aggregateId
            from AccommodationTypeAvailability a
            inner join a.accommodationAvailabilities accommodationAvailability
            inner join accommodationAvailability.restrictions accommodationRestriction
            where accommodationRestriction.entityId = ?1""")
    Optional<AggregateId> findIdByAccommodationAvailabilities_AccommodationRestrictionId(UUID accommodationRestrictionId);

}
