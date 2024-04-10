package pl.app.learning.reference.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.reference.query.dto.ReferenceContainerDto;
import pl.app.learning.reference.query.dto.ReferenceDto;
import pl.app.learning.reference.query.model.ReferenceContainerQuery;
import pl.app.learning.reference.query.model.ReferenceQuery;

@Getter
@Component
@RequiredArgsConstructor
public class ReferenceContainerQueryMapper extends BaseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        addMapper(ReferenceContainerQuery.class, ReferenceContainerDto.class, e -> modelMapper.map(e, ReferenceContainerDto.class));
        addMapper(ReferenceContainerQuery.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(ReferenceContainerQuery.class, AggregateId.class, e -> new AggregateId(e.getId()));
    }
}
