package pl.app.learning.chapter.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.model.BaseSnapshotableEntity;

import java.util.*;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_chapter")
public class ChapterEntity extends BaseSnapshotableEntity<ChapterEntity, UUID, ChapterSnapshotEntity> {
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

    @Override
    public ChapterSnapshotEntity makeSnapshot() {
        return ChapterSnapshotEntity.builder()
                .topic(topic)
                .introduction(introduction)
                .owner(this)
                .build();
    }

    @Override
    public void revertSnapshot(ChapterSnapshotEntity snapshot) {
        this.topic = snapshot.getTopic();
        this.introduction = snapshot.getIntroduction();
    }


    public void setReferences(Set<ReferenceEntity> references) {
        this.references.forEach(reference -> reference.setChapter(null));
        this.references.clear();
        references.stream()
                .peek(reference -> reference.setChapter(this))
                .forEach(this.references::add);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ChapterEntity that = (ChapterEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
