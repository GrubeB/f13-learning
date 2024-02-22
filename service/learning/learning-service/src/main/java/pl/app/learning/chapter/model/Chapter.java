package pl.app.learning.chapter.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.app.common.mapper.Join;
import pl.app.common.mapper.MergerUtils;
import pl.app.common.model.BaseSnapshotableEntity;
import pl.app.common.model.snapshot.TransientSnapshotable;
import pl.app.learning.chapter.model.snapshot.ChapterSnapshot;
import pl.app.learning.chapter.model.snapshot.ReferenceSnapshot;

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
public class Chapter extends BaseSnapshotableEntity<Chapter, UUID, ChapterSnapshot> {
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
    private Set<Reference> references = new LinkedHashSet<>();

    public void setReferences(Set<Reference> references) {
        this.references.forEach(r -> r.setChapter(null));
        this.references.clear();
        references.forEach(this::addReference);
    }

    public void addReference(Reference referenceSnapshot) {
        this.references.add(referenceSnapshot);
        referenceSnapshot.setChapter(this);
    }

    // snapshot
    @Override
    public ChapterSnapshot makeSnapshot() {
        Set<ReferenceSnapshot> referenceSnapshots = this.references.stream()
                .map(TransientSnapshotable::makeAndStoreSnapshot)
                .collect(Collectors.toSet());
        return new ChapterSnapshot(
                this,
                this.topic,
                this.introduction,
                referenceSnapshots);
    }

    @Override
    public Chapter revertSnapshot(ChapterSnapshot snapshot) {
        this.id = snapshot.getSnapshotOwnerId();
        this.topic = snapshot.getTopic();
        this.introduction = snapshot.getIntroduction();
        MergerUtils.mergeCollections(Join.RIGHT,
                this.references, snapshot.getReferences(),
                (e, s) -> e.revertSnapshot(s), Reference::new,
                Reference::getId, ReferenceSnapshot::getId);
        this.references.forEach(reference -> reference.setChapter(this));
        return this;
    }
}
