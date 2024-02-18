package pl.app.learning.chapter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.app.common.aware.RootAware;
import pl.app.common.model.BaseSnapshotableEntity;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_chapter_reference")
public class ReferenceEntity extends BaseSnapshotableEntity<ReferenceEntity, UUID, ReferenceEntitySnapshot> implements
        RootAware<ChapterEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "reference_id", nullable = false)
    private UUID id;

    @Column(name = "reference_name")
    private String name;

    @Column(nullable = false)
    private String link;

    @ManyToOne
    @JoinColumn(name = "chapter_id")
    @ToString.Exclude
    @JsonIgnore
    private ChapterEntity chapter;

    @Override
    public ChapterEntity root() {
        return chapter;
    }
    @Override
    public ReferenceEntitySnapshot makeSnapshot() {
        return new ReferenceEntitySnapshot(this, name, link);
    }

    @Override
    public ReferenceEntity revertSnapshot(ReferenceEntitySnapshot snapshot) {
        this.id = snapshot.getSnapshotOwnerId();
        this.name = snapshot.getName();
        this.link = snapshot.getLink();
        return this;
    }
}
