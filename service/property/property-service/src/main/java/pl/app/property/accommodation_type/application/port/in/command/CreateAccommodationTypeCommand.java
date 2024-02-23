package pl.app.property.accommodation_type.application.port.in.command;

import lombok.*;
import pl.app.common.cqrs.command.annotation.CommandAnnotation;
import pl.app.property.accommodation_type_details.application.domain.model.GenderRoomType;
import pl.app.property.accommodation_type_details.application.domain.model.RoomType;

import java.io.Serializable;
import java.util.UUID;

@CommandAnnotation
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccommodationTypeCommand implements
        Serializable {
    private UUID propertyId;
    private String name;
    private String abbreviation;
    private String description;
    private GenderRoomType genderRoomType;
    private RoomType roomType;
}