package pl.app.property.accommodation_type.application.port.in.command;

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
public class RemoveAccommodationCommand implements
        Serializable {
    private UUID accommodationId;
    private UUID accommodationTypeId;
}