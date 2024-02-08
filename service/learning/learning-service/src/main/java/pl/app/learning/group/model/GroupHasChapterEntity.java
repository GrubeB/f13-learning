package pl.app.learning.group.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.aware.RootAware;
import pl.app.common.model.AbstractEntity;
import pl.app.learning.chapter.model.ChapterEntity;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_group_has_chapter")
public class GroupHasChapterEntity extends AbstractEntity<UUID> implements
        RootAware<GroupEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "group_has_chapter_id", nullable = false)
    private UUID groupHasChapterId;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @ToString.Exclude
    @JsonIgnore
    private GroupEntity group;

    @ManyToOne
    @JoinColumn(name = "chapter_id")
    private ChapterEntity chapter;

    @Override
    public UUID getId() {
        return this.groupHasChapterId;
    }

    @Override
    public GroupEntity root() {
        return group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GroupHasChapterEntity that = (GroupHasChapterEntity) o;
        return groupHasChapterId != null && Objects.equals(groupHasChapterId, that.groupHasChapterId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}