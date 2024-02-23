package pl.app.property.accommodation_type_details.application.port.in.command;

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
public class UpdateAccommodationTypeDetailsCommand implements Serializable {
    private UUID accommodationTypeId;
    private String name;
    private String abbreviation;
    private String description;
    private GenderRoomType genderRoomType;
    private RoomType roomType;
}
