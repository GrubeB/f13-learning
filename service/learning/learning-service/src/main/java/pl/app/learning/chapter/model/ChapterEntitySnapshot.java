package pl.app.learning.chapter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import pl.app.common.model.BaseSnapshotEntity;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@Table(name = "t_chapter_snapshot")
public class ChapterEntitySnapshot extends BaseSnapshotEntity<ChapterEntity, UUID, ChapterEntitySnapshot> {
    private String topic;
    @Column(length = 8_000)
    private String introduction;
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "chapter",
            orphanRemoval = true)
    @JsonIgnore
    private Set<ReferenceEntitySnapshot> references = new LinkedHashSet<>();

    public ChapterEntitySnapshot() {
    }

    public ChapterEntitySnapshot(ChapterEntity chapter, String topic, String introduction, Set<ReferenceEntitySnapshot> references) {
        super(chapter);
        this.topic = topic;
        this.introduction = introduction;
        this.references = references;
        this.references.stream()
                .peek(reference -> reference.setChapter(this))
                .forEach(this.references::add);
    }
}
