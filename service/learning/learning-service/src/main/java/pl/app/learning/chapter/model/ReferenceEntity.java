package pl.app.learning.chapter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.aware.RootAware;
import pl.app.common.model.BaseAuditEntity;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_chapter_reference")
public class ReferenceEntity extends BaseAuditEntity<UUID> implements
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
    public UUID getId() {
        return this.id;
    }

    @Override
    public ChapterEntity root() {
        return chapter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ReferenceEntity that = (ReferenceEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
