package pl.app.learning.progress.application.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.app.common.ddd.BaseJpaAuditDomainEntity;
import pl.app.common.ddd.annotation.EntityAnnotation;

import java.util.UUID;

@EntityAnnotation
@Entity
@Setter
@Getter
@Table(name = "t_progress")
public class Progress extends BaseJpaAuditDomainEntity<Progress> {
    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "progress_type", nullable = false)
    private ProgressType type;

    @ManyToOne
    @JoinColumn(name = "container_id")
    private ProgressContainer container;

    @SuppressWarnings("unused")
    protected Progress() {
        super();
    }

    public Progress(UUID userId, ProgressType type, ProgressContainer container) {
        super();
        this.userId = userId;
        this.type = type;
        this.container = container;
    }

    public void setProgressType(ProgressType type) {
        this.type = type;
    }
}

