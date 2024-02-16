package pl.app.learning.chapter.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import pl.app.common.model.BaseSnapshotEntity;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_chapter_snapshot")
public class ChapterSnapshotEntity extends BaseSnapshotEntity<ChapterEntity, UUID> {
    private String topic;
    @Column(length = 8_000)
    private String introduction;


}
