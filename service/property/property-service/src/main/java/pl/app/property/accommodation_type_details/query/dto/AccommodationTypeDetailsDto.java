package pl.app.property.accommodation_type_details.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.property.accommodation_type_details.application.domain.model.GenderRoomType;
import pl.app.property.accommodation_type_details.application.domain.model.RoomType;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationTypeDetailsDto implements Serializable {
    private UUID accommodationTypeDetailsId;
    private String name;
    private String abbreviation;
    private String description;
    private GenderRoomType genderRoomType;
    private RoomType roomType;
}
