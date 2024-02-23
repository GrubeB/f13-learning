package pl.app.property.accommodation_type.query.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.model.BaseAuditEntity;

import java.util.UUID;

@Entity
@Immutable
@Getter
@Setter
@Table(name = "t_accommodation")
public class AccommodationQuery extends BaseAuditEntity<AccommodationQuery, UUID> {
    @Id
    private UUID id;
    @Column(name = "accommodation_name", nullable = false)
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "accommodation_type", nullable = false)
    private AccommodationTypeQuery accommodationTypeQuery;
}
