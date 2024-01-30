package pl.app.property.accommodation_type.application.port.out;


import pl.app.ddd.AggregateId;
import pl.app.property.accommodation_type_details.model.GenderRoomType;
import pl.app.property.accommodation_type_details.model.RoomType;

public interface CreateAccommodationTypeDetailsPort {
    void create(AggregateId accommodationTypeId, String name, String abbreviation, String description, GenderRoomType genderRoomType, RoomType roomType);
}
