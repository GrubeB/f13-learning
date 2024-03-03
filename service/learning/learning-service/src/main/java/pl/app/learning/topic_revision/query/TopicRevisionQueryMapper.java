package pl.app.learning.topic_revision.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.topic_revision.application.domain.TopicHasCategoryRevision;
import pl.app.learning.topic_revision.application.domain.TopicRevision;
import pl.app.learning.topic_revision.query.dto.TopicRevisionDto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Component
@RequiredArgsConstructor
public class TopicRevisionQueryMapper extends BaseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        initTopicRevisionToTopiRevisionDto();

        addMapper(TopicRevision.class, TopicRevisionDto.class, e -> modelMapper.map(e, TopicRevisionDto.class));
        addMapper(TopicRevision.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(TopicRevision.class, AggregateId.class, e -> new AggregateId(e.getId()));
    }

    private void initTopicRevisionToTopiRevisionDto() {
        TypeMap<TopicRevision, TopicRevisionDto> typeMap = modelMapper.createTypeMap(TopicRevision.class, TopicRevisionDto.class);

        Converter<Set<TopicHasCategoryRevision>, List<UUID>> categoryConverter = context -> context.getSource().stream()
                .map(TopicHasCategoryRevision::getCategory)
                .map(AggregateId::getId)
                .toList();
        typeMap.addMappings(mapper -> mapper.using(categoryConverter).map(TopicRevision::getCategories, TopicRevisionDto::setCategories));
    }
}
