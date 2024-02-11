package pl.app.property.accommodation_availability.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccommodationTypeAvailabilityCommand implements Serializable {
    private UUID propertyId;
    private UUID accommodationTypeId;
}