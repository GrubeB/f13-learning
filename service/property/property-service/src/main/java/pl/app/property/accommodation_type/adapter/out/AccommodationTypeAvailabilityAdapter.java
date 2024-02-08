package pl.app.property.accommodation_type.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.cqrs.command.gateway.CommandGateway;
import pl.app.ddd.AggregateId;
import pl.app.property.accommodation_availability.application.port.in.command.CreateAccommodationTypeAvailabilityCommand;
import pl.app.property.accommodation_type.application.port.out.CreateAccommodationTypeAvailabilityPort;

@Component
@Transactional
@RequiredArgsConstructor
class AccommodationTypeAvailabilityAdapter implements
        CreateAccommodationTypeAvailabilityPort {
    private final CommandGateway gateway;

    @Override
    public void create(AggregateId accommodationTypeId, AggregateId propertyId) {
        gateway.sendAsync(new CreateAccommodationTypeAvailabilityCommand(accommodationTypeId, propertyId));
    }

}
