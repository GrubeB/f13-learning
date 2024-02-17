package pl.app.learning.chapter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import pl.app.common.model.BaseSnapshotEntity;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_chapter_reference_snapshot")
public class ReferenceEntitySnapshot extends BaseSnapshotEntity<ReferenceEntity, UUID, ReferenceEntitySnapshot> {
    @Column(name = "reference_name")
    private String name;
    private String link;

    @ManyToOne
    @PrimaryKeyJoinColumn
    @ToString.Exclude
    @JsonIgnore
    private ChapterEntitySnapshot chapter;

    public ReferenceEntitySnapshot(ReferenceEntity owner, String name, String link) {
        super(owner);
        this.name = name;
        this.link = link;
    }
}
