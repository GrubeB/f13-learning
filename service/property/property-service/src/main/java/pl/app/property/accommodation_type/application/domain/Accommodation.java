package pl.app.property.accommodation_type.application.domain;


import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.BaseJpaAuditDomainAggregateRoot;
import pl.app.common.ddd.annotation.EntityAnnotation;

@EntityAnnotation
@Entity
@Getter
@Table(name = "t_accommodation")
public class Accommodation extends BaseJpaAuditDomainAggregateRoot<AccommodationType> {
    @Column(name = "accommodation_name", nullable = false)
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "accommodation_type", nullable = false)
    private AccommodationType accommodationType;

    @SuppressWarnings("unused")
    protected Accommodation() {
        super();
    }

    public Accommodation(AccommodationType accommodationType, String name, String description) {
        super();
        this.accommodationType = accommodationType;
        this.name = name;
        this.description = description;
    }
}
