package pl.app.learning.path.application.domain;


import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainEntity;
import pl.app.common.ddd.annotation.EntityAnnotation;

@EntityAnnotation
@Entity
@Getter
@Table(name = "t_path_has_category")
public class PathHasCategory extends BaseJpaAuditDomainEntity<PathHasCategory> {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "category_id", nullable = false, updatable = false))
    })
    private AggregateId category;


    @ManyToOne(optional = false)
    @JoinColumn(name = "path_id", nullable = false, updatable = false)
    private Path path;

    @SuppressWarnings("unused")
    protected PathHasCategory() {
        super();
    }

    public PathHasCategory(AggregateId category) {
        this.category = category;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}

