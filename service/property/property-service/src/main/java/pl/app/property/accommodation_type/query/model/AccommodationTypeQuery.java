package pl.app.property.accommodation_type.query.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.model.BaseAuditEntity;
import pl.app.property.accommodation_type_details.application.domain.AccommodationTypeDetails;

import java.util.Set;
import java.util.UUID;

@Entity
@Immutable
@Getter
@Setter
@Table(name = "t_accommodation_type")
public class AccommodationTypeQuery extends BaseAuditEntity<AccommodationTypeQuery, UUID> {
    @Id
    private UUID id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "accommodationTypeQuery")
    private Set<AccommodationQuery> accommodations;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "accommodationType")
    private AccommodationTypeDetails accommodationTypeDetails;
}
