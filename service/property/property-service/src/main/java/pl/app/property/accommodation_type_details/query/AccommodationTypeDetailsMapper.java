package pl.app.property.accommodation_type_details.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.shared.dto.BaseDto;
import pl.app.property.accommodation_type.adapter.out.persistence.AccommodationTypeEntity;
import pl.app.property.accommodation_type.query.AccommodationTypeQueryService;
import pl.app.property.accommodation_type_details.application.domain.model.AccommodationTypeDetailsEntity;
import pl.app.property.accommodation_type_details.application.port.in.command.CreateAccommodationTypeDetailsCommand;
import pl.app.property.accommodation_type_details.application.port.in.command.UpdateAccommodationTypeDetailsCommand;
import pl.app.property.accommodation_type_details.query.dto.AccommodationTypeDetailsDto;

@Getter
@Component
@RequiredArgsConstructor
public class AccommodationTypeDetailsMapper extends BaseMapper {
    private final ModelMapper modelMapper;
    private final AccommodationTypeQueryService accommodationTypeQueryService;

    @PostConstruct
    void init() {
        addMapper(AccommodationTypeDetailsEntity.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(AccommodationTypeDetailsEntity.class, AccommodationTypeDetailsDto.class, e -> modelMapper.map(e, AccommodationTypeDetailsDto.class));
        addMapper(UpdateAccommodationTypeDetailsCommand.class, AccommodationTypeDetailsEntity.class, e -> modelMapper.map(e, AccommodationTypeDetailsEntity.class));
        addMapper(CreateAccommodationTypeDetailsCommand.class, AccommodationTypeDetailsEntity.class, this::mapToAccommodationTypeDetailsEntity);
    }

    AccommodationTypeDetailsEntity mapToAccommodationTypeDetailsEntity(CreateAccommodationTypeDetailsCommand command) {
        AccommodationTypeEntity accommodationTypeEntity = accommodationTypeQueryService.fetchById(command.getAccommodationTypeId());
        AccommodationTypeEntity reference = new AccommodationTypeEntity();
        reference.setAccommodationTypeId(command.getAccommodationTypeId());
        return new AccommodationTypeDetailsEntity(accommodationTypeEntity);
    }
}
