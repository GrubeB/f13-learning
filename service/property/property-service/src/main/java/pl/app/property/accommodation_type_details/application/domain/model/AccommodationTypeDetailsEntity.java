package pl.app.property.accommodation_type_details.application.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.model.BaseAuditEntity;
import pl.app.property.accommodation_type.adapter.out.persistence.AccommodationTypeEntity;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_accommodation_type_details")
public class AccommodationTypeDetailsEntity extends BaseAuditEntity<UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "type_id", nullable = false)
    private UUID accommodationTypeDetailsId;
    @Column(name = "accommodation_name")
    private String name;
    private String abbreviation;
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender_room_type")
    private GenderRoomType genderRoomType;
    @Enumerated(EnumType.STRING)
    @Column(name = "room_type")
    private RoomType roomType;

    @OneToOne
    @JoinColumn(name = "accommodation_type_id")
    @ToString.Exclude
    @JsonIgnore
    private AccommodationTypeEntity accommodationType;

    public AccommodationTypeDetailsEntity(AccommodationTypeEntity accommodationType) {
        this.accommodationType = accommodationType;
    }

    @Override
    public UUID getId() {
        return accommodationTypeDetailsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AccommodationTypeDetailsEntity that = (AccommodationTypeDetailsEntity) o;
        return accommodationTypeDetailsId != null && Objects.equals(accommodationTypeDetailsId, that.accommodationTypeDetailsId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
