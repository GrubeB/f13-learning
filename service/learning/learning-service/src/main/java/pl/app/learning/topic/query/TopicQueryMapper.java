package pl.app.learning.topic.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.category.query.CategoryQueryMapper;
import pl.app.learning.reference.query.ReferenceQueryMapper;
import pl.app.learning.topic.query.dto.TopicDto;
import pl.app.learning.topic.query.model.TopicQuery;

@Getter
@Component
@RequiredArgsConstructor
public class TopicQueryMapper extends BaseMapper {
    private final ModelMapper modelMapper;
    private final CategoryQueryMapper categoryQueryMapper;
    private final ReferenceQueryMapper referenceQueryMapper;

    @PostConstruct
    void init() {
        modelMapper.addMappings(new PropertyMap<TopicQuery, TopicDto>() {
            @Override
            protected void configure() {
                map(source.getComment().getComments()).setComments(null);
                map(source.getReference().getReferences()).setReferences(null);
                map(source.getProgress().getProgresses()).setProgresses(null);
            }
        });
        addMapper(TopicQuery.class, TopicDto.class, e -> modelMapper.map(e, TopicDto.class));
        addMapper(TopicQuery.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(TopicQuery.class, AggregateId.class, e -> new AggregateId(e.getId()));
    }
}
