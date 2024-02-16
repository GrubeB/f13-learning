package pl.app.learning.topic.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.topic.query.dto.TopicDto;

@Getter
@Component
@RequiredArgsConstructor
public class TopicQueryMapper extends BaseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        addMapper(TopicQueryEntity.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(TopicQueryEntity.class, TopicDto.class, e -> modelMapper.map(e, TopicDto.class));
    }
}
