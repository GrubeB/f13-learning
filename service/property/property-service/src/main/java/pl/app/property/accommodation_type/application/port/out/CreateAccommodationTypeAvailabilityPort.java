package pl.app.property.accommodation_type.application.port.out;


import pl.app.ddd.AggregateId;

public interface CreateAccommodationTypeAvailabilityPort {
    void create(AggregateId accommodationTypeId, AggregateId propertyId);
}
