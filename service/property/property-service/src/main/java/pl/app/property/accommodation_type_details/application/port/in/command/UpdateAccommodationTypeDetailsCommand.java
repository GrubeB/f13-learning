package pl.app.property.accommodation_type_details.application.port.in.command;

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
public class UpdateAccommodationTypeDetailsCommand implements Serializable {
    private UUID accommodationTypeId;
    private String name;
    private String abbreviation;
    private String description;
    private GenderRoomType genderRoomType;
    private RoomType roomType;
}
