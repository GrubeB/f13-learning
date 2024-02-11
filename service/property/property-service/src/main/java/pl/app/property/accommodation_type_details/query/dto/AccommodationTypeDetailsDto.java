package pl.app.property.accommodation_type_details.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.property.accommodation_type_details.application.domain.model.GenderRoomType;
import pl.app.property.accommodation_type_details.application.domain.model.RoomType;
import pl.app.common.shared.dto.BaseDto;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
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