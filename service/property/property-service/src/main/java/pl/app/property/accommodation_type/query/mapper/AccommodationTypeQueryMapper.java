package pl.app.property.accommodation_type.query.mapper;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.mapper.Mapper;
import pl.app.common.shared.dto.BaseDto;
import pl.app.property.accommodation_type.adapter.out.persistence.model.AccommodationEntity;
import pl.app.property.accommodation_type.adapter.out.persistence.model.AccommodationTypeEntity;
import pl.app.property.accommodation_type.query.dto.AccommodationTypeDto;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Getter
@Component
@RequiredArgsConstructor
public class AccommodationTypeQueryMapper implements
        Mapper {
    private final ModelMapper modelMapper;
    private final Map<AbstractMap.SimpleEntry<Class<?>, Class<?>>, Function<?, ?>> mappers = new HashMap<>();
    @PostConstruct
    void init() {
        addMapper(AccommodationTypeEntity.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(AccommodationTypeEntity.class, AccommodationTypeDto.class, e -> modelMapper.map(e, AccommodationTypeDto.class));
        addMapper(AccommodationEntity.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(AccommodationEntity.class, AccommodationTypeDto.AccommodationDto.class, e -> modelMapper.map(e, AccommodationTypeDto.AccommodationDto.class));
    }
}
