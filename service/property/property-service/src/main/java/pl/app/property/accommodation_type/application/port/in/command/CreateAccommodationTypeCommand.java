package pl.app.property.accommodation_type.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.app.property.accommodation_type_details.model.GenderRoomType;
import pl.app.property.accommodation_type_details.model.RoomType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccommodationTypeCommand implements Serializable {
    private UUID propertyId;
    private String name;
    private String abbreviation;
    private String description;
    private GenderRoomType genderRoomType;
    private RoomType roomType;
}