package pl.app.property.accommodation_type_details.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.shared.dto.BaseDto;
import pl.app.property.accommodation_type.adapter.out.persistence.AccommodationType;
import pl.app.property.accommodation_type.query.AccommodationTypeQueryService;
import pl.app.property.accommodation_type.query.model.AccommodationTypeQuery;
import pl.app.property.accommodation_type_details.application.domain.model.AccommodationTypeDetails;
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
        // to dto
        addMapper(AccommodationTypeDetails.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(AccommodationTypeDetails.class, AccommodationTypeDetailsDto.class, e -> modelMapper.map(e, AccommodationTypeDetailsDto.class));
        addMapper(AccommodationTypeDetails.class, AggregateId.class, e -> new AggregateId(e.getId()));
        // to entity
        addMapper(UpdateAccommodationTypeDetailsCommand.class, AccommodationTypeDetails.class, e -> modelMapper.map(e, AccommodationTypeDetails.class));
        addMapper(CreateAccommodationTypeDetailsCommand.class, AccommodationTypeDetails.class, this::create);
        // merge\
        addMerger(AccommodationTypeDetails.class, this::mergeAccommodationTypeDetailsEntity);
    }

    AccommodationTypeDetails create(CreateAccommodationTypeDetailsCommand command) {
        AccommodationTypeQuery accommodationType = accommodationTypeQueryService.fetchById(command.getAccommodationTypeId());
        return new AccommodationTypeDetails(accommodationType);
    }

    private AccommodationTypeDetails mergeAccommodationTypeDetailsEntity(AccommodationTypeDetails target, AccommodationTypeDetails source) {
        if (target == null || source == null) {
            return target;
        }
        if (source.getId() != null) {
            target.setId(source.getId());
        }
        if (source.getName() != null) {
            target.setName(source.getName());
        }
        if (source.getAbbreviation() != null) {
            target.setAbbreviation(source.getAbbreviation());
        }
        if (source.getDescription() != null) {
            target.setDescription(source.getDescription());
        }
        if (source.getGenderRoomType() != null) {
            target.setGenderRoomType(source.getGenderRoomType());
        }
        if (source.getRoomType() != null) {
            target.setRoomType(source.getRoomType());
        }
        if (source.getAccommodationType() != null) {
            target.setAccommodationType(source.getAccommodationType());
        }
        return target;
    }
}
