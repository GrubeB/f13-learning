package pl.app.property.accommodation_availability.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoveAccommodationAvailabilityCommand implements Serializable {
    private UUID accommodationTypeId;
    private UUID accommodationId;
}