package pl.app.property.accommodation_type_details.mapper;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.property.accommodation_type_details.dto.AccommodationTypeDetailsCreateDto;
import pl.app.property.accommodation_type_details.dto.AccommodationTypeDetailsDto;
import pl.app.property.accommodation_type_details.model.AccommodationTypeDetailsEntity;
import pl.app.common.mapper.Mapper;
import pl.app.common.shared.dto.BaseDto;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Getter
@Component
@RequiredArgsConstructor
public class AccommodationTypeDetailsMapper implements Mapper {
    private final ModelMapper modelMapper;

    private final Map<AbstractMap.SimpleEntry<Class<?>, Class<?>>, Function<?, ?>> mappers = new HashMap<>();

    @PostConstruct
    void init() {
        addMapper(AccommodationTypeDetailsEntity.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(AccommodationTypeDetailsEntity.class, AccommodationTypeDetailsDto.class, e -> modelMapper.map(e, AccommodationTypeDetailsDto.class));
        addMapper(AccommodationTypeDetailsCreateDto.class, AccommodationTypeDetailsEntity.class, e -> modelMapper.map(e, AccommodationTypeDetailsEntity.class));
    }
}
