package pl.app.learning.progress.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.progress.query.dto.ProgressContainerDto;
import pl.app.learning.progress.query.model.ProgressContainerQuery;

@Getter
@Component
@RequiredArgsConstructor
public class ProgressContainerQueryMapper extends BaseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        addMapper(ProgressContainerQuery.class, ProgressContainerDto.class, e -> modelMapper.map(e, ProgressContainerDto.class));
        addMapper(ProgressContainerQuery.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(ProgressContainerQuery.class, AggregateId.class, e -> new AggregateId(e.getId()));
    }
}
