package pl.app.learning.chapter.service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.mapper.Join;
import pl.app.common.mapper.MergerUtils;
import pl.app.learning.chapter.model.ChapterEntity;
import pl.app.learning.chapter.model.ReferenceEntity;

import java.util.Set;

@Getter
@Component
@RequiredArgsConstructor
public class ChapterMapper extends BaseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        addMerger(ChapterEntity.class, this::mergeChapterEntity);
        addMerger(ReferenceEntity.class, this::mergeReferenceEntity);
    }

    private ChapterEntity mergeChapterEntity(ChapterEntity target, ChapterEntity source) {
        if (target == null || source == null) {
            return target;
        }
        if (source.getTopic() != null) {
            target.setTopic(source.getTopic());
        }
        if (source.getIntroduction() != null) {
            target.setTopic(source.getIntroduction());
        }
        if (source.getReferences() != null) {
            Set<ReferenceEntity> merged = MergerUtils.mergeCollections(Join.RIGHT,
                    target.getReferences(), source.getReferences(),
                    this::mergeReferenceEntity, ReferenceEntity::getId);
            target.setReferences(merged);
        }
        return target;
    }

    private ReferenceEntity mergeReferenceEntity(ReferenceEntity target, ReferenceEntity source) {
        if (target == null || source == null) {
            return target;
        }
        if (source.getName() != null) {
            target.setName(source.getName());
        }
        if (source.getLink() != null) {
            target.setLink(source.getLink());
        }
        return target;
    }
}
