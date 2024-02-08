package pl.app.property.accommodation_availability.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccommodationTypeAvailabilityEntityRepository extends
        JpaRepository<AccommodationTypeAvailabilityEntity, UUID>,
        JpaSpecificationExecutor<AccommodationTypeAvailabilityEntity> {
    Optional<AccommodationTypeAvailabilityEntity> findByAccommodationTypeAvailabilityId(UUID accommodationTypeAvailabilityId);

    @Query("select a from AccommodationTypeAvailabilityEntity a where a.accommodationType.accommodationTypeId = ?1")
    Optional<AccommodationTypeAvailabilityEntity> findByAccommodationType_AccommodationTypeId(UUID accommodationTypeId);

    @Query("""
            select a from AccommodationTypeAvailabilityEntity a inner join a.accommodationType.accommodations accommodations
            where accommodations.accommodationId = ?1""")
    Optional<AccommodationTypeAvailabilityEntity> findByAccommodationType_Accommodations_AccommodationId(UUID accommodationId);

    @Query("""
            select a from AccommodationTypeAvailabilityEntity a inner join a.accommodationTypeReservations accommodationTypeReservations
            where accommodationTypeReservations.accommodationTypeReservationId = ?1""")
    Optional<AccommodationTypeAvailabilityEntity> findByAccommodationTypeReservations_AccommodationTypeReservationId(UUID accommodationTypeReservationId);

    @Query("""
            select a from AccommodationTypeAvailabilityEntity a inner join a.accommodationRestrictions accommodationRestrictions
            where accommodationRestrictions.accommodationRestrictionId = ?1""")
    Optional<AccommodationTypeAvailabilityEntity> findByAccommodationRestrictions_AccommodationRestrictionId(UUID accommodationRestrictionId);



}
