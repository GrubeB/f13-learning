package pl.app.property.accommodation_type.application.domain.event;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationCreatedEvent implements
        Serializable {
    private UUID propertyId;
    private UUID accommodationTypeId;
    private UUID accommodationId;
}