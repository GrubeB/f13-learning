package pl.app.learning.chapter.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.model.AbstractEntity;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_chapter")
public class ChapterEntity extends AbstractEntity<UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "chapter_id", nullable = false)
    private UUID chapterId;

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
    public UUID getId() {
        return this.chapterId;
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
        return chapterId != null && Objects.equals(chapterId, that.chapterId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
