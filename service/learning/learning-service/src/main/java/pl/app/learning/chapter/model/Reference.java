package pl.app.learning.chapter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.app.common.aware.RootAware;
import pl.app.common.model.BaseSnapshotableEntity;
import pl.app.learning.chapter.model.snapshot.ReferenceSnapshot;

import java.util.UUID;

@Entity(name = "referencccce")
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_chapter_reference")
public class Reference extends BaseSnapshotableEntity<Reference, UUID, ReferenceSnapshot> implements
        RootAware<Chapter> {
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
    private Chapter chapter;

    @Override
    public Chapter root() {
        return chapter;
    }

    // snapshot
    @Override
    public ReferenceSnapshot makeSnapshot() {
        return new ReferenceSnapshot(this, name, link);
    }

    @Override
    public Reference revertSnapshot(ReferenceSnapshot snapshot) {
        this.id = snapshot.getSnapshotOwnerId();
        this.name = snapshot.getName();
        this.link = snapshot.getLink();
        return this;
    }
}
