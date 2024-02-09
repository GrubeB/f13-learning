package pl.app.property.accommodation_type_details.application.port.in.command;

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
public class CreateAccommodationTypeDetailsCommand implements Serializable {
    private UUID accommodationTypeId;
}
