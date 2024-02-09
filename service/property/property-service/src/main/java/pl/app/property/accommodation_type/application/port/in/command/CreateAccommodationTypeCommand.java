package pl.app.property.accommodation_type.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.app.property.accommodation_type_details.application.domain.model.GenderRoomType;
import pl.app.property.accommodation_type_details.application.domain.model.RoomType;

import java.io.Serializable;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateAccommodationTypeCommand implements
        Serializable {
    private UUID propertyId;
    private String name;
    private String abbreviation;
    private String description;
    private GenderRoomType genderRoomType;
    private RoomType roomType;
}