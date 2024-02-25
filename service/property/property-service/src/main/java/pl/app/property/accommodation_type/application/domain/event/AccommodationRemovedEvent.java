package pl.app.property.accommodation_type.application.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationRemovedEvent implements
        Serializable {
    private UUID propertyId;
    private UUID accommodationTypeId;
    private UUID accommodationId;
}