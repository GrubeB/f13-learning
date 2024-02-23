package pl.app.property.accommodation_type.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.shared.dto.BaseDto;
import pl.app.property.accommodation_type.query.dto.AccommodationTypeDto;
import pl.app.property.accommodation_type.query.model.AccommodationTypeQuery;

@Getter
@Component
@RequiredArgsConstructor
public class AccommodationTypeQueryMapper extends BaseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        addMapper(AccommodationTypeQuery.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(AccommodationTypeQuery.class, AccommodationTypeDto.class, e -> modelMapper.map(e, AccommodationTypeDto.class));
        addMapper(AccommodationTypeQuery.class, AggregateId.class, e -> new AggregateId(e.getId()));
    }
}
