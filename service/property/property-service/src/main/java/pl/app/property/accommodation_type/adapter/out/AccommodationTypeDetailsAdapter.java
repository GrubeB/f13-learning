package pl.app.property.accommodation_type.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.cqrs.command.gateway.CommandGateway;
import pl.app.ddd.AggregateId;
import pl.app.property.accommodation_type.application.port.out.CreateAccommodationTypeDetailsPort;
import pl.app.property.accommodation_type_details.dto.AccommodationTypeDetailsCreateDto;
import pl.app.property.accommodation_type_details.model.GenderRoomType;
import pl.app.property.accommodation_type_details.model.RoomType;

import java.util.UUID;

@Component
@Transactional
@RequiredArgsConstructor
class AccommodationTypeDetailsAdapter implements
        CreateAccommodationTypeDetailsPort {
    private final CommandGateway gateway;
    @Override
    public void create(AggregateId accommodationTypeId, String name, String abbreviation, String description,
                       GenderRoomType genderRoomType, RoomType roomType) {
        gateway.sendAsync(new AccommodationTypeDetailsCreateDto(accommodationTypeId.getId(), name, abbreviation, description,
                genderRoomType, roomType));
    }
}
