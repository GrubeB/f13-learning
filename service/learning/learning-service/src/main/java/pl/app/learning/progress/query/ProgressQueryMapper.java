package pl.app.learning.progress.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.progress.query.dto.ProgressContainerDto;
import pl.app.learning.progress.query.dto.ProgressDto;
import pl.app.learning.progress.query.model.ProgressContainerQuery;
import pl.app.learning.progress.query.model.ProgressQuery;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Component
@RequiredArgsConstructor
public class ProgressQueryMapper extends BaseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        modelMapper.addMappings(new PropertyMap<ProgressQuery, ProgressDto>() {
            @Override
            protected void configure() {
                map().setDomainObject(source.getContainer().getDomainObject());
                map().setDomainObjectType(source.getContainer().getDomainObjectType());
            }
        });

        addMapper(ProgressQuery.class, ProgressDto.class, e -> modelMapper.map(e, ProgressDto.class));
        addMapper(ProgressQuery.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(ProgressQuery.class, AggregateId.class, e -> new AggregateId(e.getId()));
    }
}
