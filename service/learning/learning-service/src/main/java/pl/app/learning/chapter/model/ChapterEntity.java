package pl.app.learning.chapter.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.app.common.mapper.Join;
import pl.app.common.mapper.MergerUtils;
import pl.app.common.model.BaseSnapshotableEntity;
import pl.app.common.model.snapshot.TransientSnapshotable;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_chapter")
public class ChapterEntity extends BaseSnapshotableEntity<ChapterEntity, UUID, ChapterEntitySnapshot> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "chapter_id", nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String topic;

    @Column(length = 8_000)
    private String introduction;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "chapter",
            orphanRemoval = true)
    @Builder.Default
    private Set<ReferenceEntity> references = new LinkedHashSet<>();

    public void setReferences(Set<ReferenceEntity> references) {
        this.references.forEach(reference -> reference.setChapter(null));
        this.references = references;
        references.stream()
                .peek(reference -> reference.setChapter(this))
                .forEach(this.references::add);
    }


    // snapshot
    @Override
    public ChapterEntitySnapshot makeSnapshot() {
        Set<ReferenceEntitySnapshot> referenceEntitySnapshots = this.references.stream()
                .map(TransientSnapshotable::makeAndStoreSnapshot)
                .collect(Collectors.toSet());
        return new ChapterEntitySnapshot(
                this,
                this.topic,
                this.introduction,
                referenceEntitySnapshots);
    }

    @Override
    public ChapterEntity revertSnapshot(ChapterEntitySnapshot snapshot) {
        this.id = snapshot.getSnapshotOwnerId();
        this.topic = snapshot.getTopic();
        this.introduction = snapshot.getIntroduction();
        MergerUtils.mergeCollections(Join.RIGHT,
                this.references, snapshot.getReferences(),
                (e, s) -> e.revertSnapshot(s), ReferenceEntity::new,
                ReferenceEntity::getId, ReferenceEntitySnapshot::getId);
        this.references.forEach(reference -> reference.setChapter(this));
        return this;
    }
}
