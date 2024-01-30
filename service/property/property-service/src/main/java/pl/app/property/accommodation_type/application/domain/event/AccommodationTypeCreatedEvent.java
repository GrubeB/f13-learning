package pl.app.property.accommodation_type.application.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationTypeCreatedEvent implements Serializable {
    private UUID accommodationTypeId;
}