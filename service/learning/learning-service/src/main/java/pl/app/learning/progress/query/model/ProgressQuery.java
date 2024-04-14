package pl.app.learning.progress.query.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.model.BaseAuditEntity;
import pl.app.learning.progress.application.domain.ProgressType;

import java.util.UUID;

@Entity
@Immutable
@Getter
@Setter
@Table(name = "t_progress")
public class ProgressQuery extends BaseAuditEntity<ProgressQuery, UUID> {
    @Id
    private UUID id;
    @Column(name = "user_id")
    private UUID userId;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "progress_type")
    private ProgressType type;

    @ManyToOne
    @JoinColumn(name = "container_id")
    private ProgressContainerQuery container;
}
