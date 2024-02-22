package pl.app.learning.chapter.model.snapshot;

import jakarta.persistence.*;
import lombok.*;
import pl.app.common.model.BaseSnapshotEntity;
import pl.app.learning.chapter.model.Chapter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@Table(name = "t_chapter_snapshot")
public class ChapterSnapshot extends BaseSnapshotEntity<Chapter, UUID, ChapterSnapshot> {
    private String topic;
    @Column(length = 8_000)
    private String introduction;
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "chapter",
            orphanRemoval = true)
    private Set<ReferenceSnapshot> references = new LinkedHashSet<>();

    public ChapterSnapshot() {
    }

    public ChapterSnapshot(Chapter chapter, String topic, String introduction, Set<ReferenceSnapshot> references) {
        super(chapter);
        this.topic = topic;
        this.introduction = introduction;
        references.forEach(this::addReference);
    }

    public void setReferences(Set<ReferenceSnapshot> references) {
        this.references.forEach(r -> r.setChapter(null));
        this.references.clear();
        references.forEach(this::addReference);
    }

    public void addReference(ReferenceSnapshot referenceSnapshot) {
        this.references.add(referenceSnapshot);
        referenceSnapshot.setChapter(this);
    }
}
