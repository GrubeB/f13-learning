package pl.app.property.accommodation_availability.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.app.ddd.AggregateId;

import java.io.Serializable;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccommodationTypeAvailabilityCommand implements Serializable {
    private AggregateId accommodationTypeId;
    private AggregateId propertyId;
}