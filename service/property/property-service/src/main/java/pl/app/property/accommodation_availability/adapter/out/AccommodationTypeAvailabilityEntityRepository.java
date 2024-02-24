package pl.app.property.accommodation_availability.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.app.common.ddd.AggregateId;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeAvailability;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccommodationTypeAvailabilityEntityRepository extends
        JpaRepository<AccommodationTypeAvailability, AggregateId> {

    @Query("""
            select a.aggregateId
            from AccommodationTypeAvailability a
            where a.accommodationTypeId.aggregateId = ?1""")
    Optional<UUID> findIdByAccommodationType_AccommodationTypeId(UUID accommodationTypeId);


//    @Query("""
//            select a.aggregateId
//            from AccommodationTypeAvailability a inner join a.accommodationType.accommodationAvailabilities accommodationAvailabilities
//            where accommodationAvailabilities.accommodationId = ?1""")
//    Optional<UUID> findIdByAccommodationType_Accommodations_AccommodationId(UUID accommodationId);
//
//    @Query("""
//            select a.accommodationTypeAvailabilityId
//            from AccommodationTypeAvailabilityEntity a inner join a.accommodationTypeReservations accommodationTypeReservations
//            where accommodationTypeReservations.accommodationTypeReservationId = ?1""")
//    Optional<UUID> findIdByAccommodationTypeReservations_AccommodationTypeReservationId(UUID accommodationTypeReservationId);
//
//    @Query("""
//            select a.accommodationTypeAvailabilityId
//            from AccommodationTypeAvailabilityEntity a inner join a.accommodationRestrictions accommodationRestrictions
//            where accommodationRestrictions.accommodationRestrictionId = ?1""")
//    Optional<UUID> findIdByAccommodationRestrictions_AccommodationRestrictionId(UUID accommodationRestrictionId);



}
