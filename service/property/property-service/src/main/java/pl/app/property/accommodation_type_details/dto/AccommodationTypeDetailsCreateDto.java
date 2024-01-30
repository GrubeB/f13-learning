package pl.app.property.accommodation_type_details.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.app.property.accommodation_type_details.model.GenderRoomType;
import pl.app.property.accommodation_type_details.model.RoomType;

import java.io.Serializable;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationTypeDetailsCreateDto implements Serializable {
    private UUID accommodationTypeId;
    private String name;
    private String abbreviation;
    private String description;
    private GenderRoomType genderRoomType;
    private RoomType roomType;
}
