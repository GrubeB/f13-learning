package pl.app.property.accommodation_type.application.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccommodationCreatedEvent implements
        Serializable {
    private UUID accommodationId;
}