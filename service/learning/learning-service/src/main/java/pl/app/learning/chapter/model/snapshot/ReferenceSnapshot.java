package pl.app.learning.chapter.model.snapshot;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import pl.app.common.model.BaseSnapshotEntity;
import pl.app.learning.chapter.model.Reference;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_chapter_reference_snapshot")
public class ReferenceSnapshot extends BaseSnapshotEntity<Reference, UUID, ReferenceSnapshot> {
    @Column(name = "reference_name")
    private String name;
    private String link;

    @ManyToOne
    @PrimaryKeyJoinColumn
    @ToString.Exclude
    @JsonIgnore
    private ChapterSnapshot chapter;

    public ReferenceSnapshot(Reference owner, String name, String link) {
        super(owner);
        this.name = name;
        this.link = link;
    }
}
