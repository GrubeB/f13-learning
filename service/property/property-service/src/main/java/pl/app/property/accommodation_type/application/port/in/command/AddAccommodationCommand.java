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
public class AddAccommodationCommand implements
        Serializable {
    private UUID accommodationTypeId;
    private String name;
    private String description;
}