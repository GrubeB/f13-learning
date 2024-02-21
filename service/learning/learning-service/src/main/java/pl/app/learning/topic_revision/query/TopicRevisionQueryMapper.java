package pl.app.learning.topic_revision.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.topic_revision.query.dto.TopicRevisionDto;

@Getter
@Component
@RequiredArgsConstructor
public class TopicRevisionQueryMapper extends BaseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        addMapper(TopicRevisionQuery.class, TopicRevisionDto.class, e -> modelMapper.map(e, TopicRevisionDto.class));
        addMapper(TopicRevisionQuery.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(TopicRevisionQuery.class, AggregateId.class, e -> new AggregateId(e.getId()));
    }
}
