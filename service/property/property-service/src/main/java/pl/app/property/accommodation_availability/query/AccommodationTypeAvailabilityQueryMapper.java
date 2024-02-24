package pl.app.property.accommodation_availability.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.shared.dto.BaseDto;
import pl.app.property.accommodation_availability.query.dto.AccommodationTypeAvailabilityDto;
import pl.app.property.accommodation_availability.query.model.AccommodationTypeAvailabilityQuery;

@Getter
@Component
@RequiredArgsConstructor
public class AccommodationTypeAvailabilityQueryMapper extends BaseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        addMapper(AccommodationTypeAvailabilityQuery.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(AccommodationTypeAvailabilityQuery.class, AccommodationTypeAvailabilityDto.class, e -> modelMapper.map(e, AccommodationTypeAvailabilityDto.class));
        addMapper(AccommodationTypeAvailabilityQuery.class, AggregateId.class, e -> new AggregateId(e.getId()));
    }
}
