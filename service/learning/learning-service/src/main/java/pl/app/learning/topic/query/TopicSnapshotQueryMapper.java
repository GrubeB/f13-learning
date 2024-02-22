package pl.app.learning.topic.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.topic.query.dto.TopicSnapshotDto;
import pl.app.learning.topic.query.model.TopicSnapshotQuery;

@Getter
@Component
@RequiredArgsConstructor
public class TopicSnapshotQueryMapper extends BaseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        addMapper(TopicSnapshotQuery.class, TopicSnapshotDto.class, e -> modelMapper.map(e, TopicSnapshotDto.class));
        addMapper(TopicSnapshotQuery.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(TopicSnapshotQuery.class, AggregateId.class, e -> new AggregateId(e.getId()));
    }
}
