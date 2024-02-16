package pl.app.learning.group.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.model.BaseAuditEntity;

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
@Table(name = "t_group")
public class GroupEntity extends BaseAuditEntity<UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "group_id", nullable = false)
    private UUID id;

    @Column(name = "group_name", nullable = false)
    private String name;

    @Column(length = 8_000)
    private String description;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "group",
            orphanRemoval = true)
    @Builder.Default
    private Set<GroupHasChapterEntity> chapters = new LinkedHashSet<>();

    public void setChapters(Set<GroupHasChapterEntity> chapters) {
        this.chapters.forEach(chapter -> chapter.setGroup(null));
        this.chapters.clear();
        chapters.stream()
                .peek(chapter -> chapter.setGroup(this))
                .forEach(this.chapters::add);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GroupEntity that = (GroupEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
