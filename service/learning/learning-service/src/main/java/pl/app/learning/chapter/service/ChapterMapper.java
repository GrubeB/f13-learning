package pl.app.learning.chapter.service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.mapper.Join;
import pl.app.common.mapper.MergerUtils;
import pl.app.learning.chapter.model.Chapter;
import pl.app.learning.chapter.model.Reference;

@Getter
@Component
@RequiredArgsConstructor
public class ChapterMapper extends BaseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        addMerger(Chapter.class, this::mergeChapterEntity);
        addMerger(Reference.class, this::mergeReferenceEntity);
    }

    private Chapter mergeChapterEntity(Chapter target, Chapter source) {
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
            MergerUtils.mergeCollections(Join.RIGHT,
                    target.getReferences(), source.getReferences(),
                    this::mergeReferenceEntity, Reference::getId);
        }
        return target;
    }

    private Reference mergeReferenceEntity(Reference target, Reference source) {
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
