package pl.app.property.accommodation_type.application.port.in.command;

import lombok.*;
import pl.app.common.cqrs.command.annotation.CommandAnnotation;

import java.io.Serializable;
import java.util.UUID;

@CommandAnnotation
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddAccommodationCommand implements
        Serializable {
    private UUID accommodationTypeId;
    private String name;
    private String description;
}