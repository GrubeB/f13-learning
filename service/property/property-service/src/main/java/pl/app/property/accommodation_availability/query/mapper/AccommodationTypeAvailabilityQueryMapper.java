package pl.app.property.accommodation_availability.query.mapper;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.mapper.BaseMapper;
import pl.app.property.accommodation_availability.adapter.out.persistence.AccommodationTypeAvailabilityEntity;
import pl.app.property.accommodation_availability.query.dto.AccommodationTypeAvailabilityDto;
import pl.app.common.shared.dto.BaseDto;
@Getter
@Component
@RequiredArgsConstructor
public class AccommodationTypeAvailabilityQueryMapper extends BaseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        addMapper(AccommodationTypeAvailabilityEntity.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(AccommodationTypeAvailabilityEntity.class, AccommodationTypeAvailabilityDto.class, e -> modelMapper.map(e, AccommodationTypeAvailabilityDto.class));
    }
}
