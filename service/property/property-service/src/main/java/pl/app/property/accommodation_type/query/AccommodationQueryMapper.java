package pl.app.property.accommodation_type.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.shared.dto.BaseDto;
import pl.app.property.accommodation_type.query.dto.AccommodationDto;
import pl.app.property.accommodation_type.query.model.AccommodationQuery;

@Getter
@Component
@RequiredArgsConstructor
public class AccommodationQueryMapper extends BaseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        addMapper(AccommodationQuery.class, AccommodationDto.class, e -> modelMapper.map(e, AccommodationDto.class));
        addMapper(AccommodationQuery.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(AccommodationQuery.class, AggregateId.class, e -> new AggregateId(e.getId()));
    }
}
