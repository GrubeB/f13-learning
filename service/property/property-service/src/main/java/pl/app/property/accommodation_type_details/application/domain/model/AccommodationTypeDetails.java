package pl.app.property.accommodation_type_details.application.domain.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.app.common.model.BaseAuditEntity;
import pl.app.property.accommodation_type.query.model.AccommodationTypeQuery;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_accommodation_type_details")
public class AccommodationTypeDetails extends BaseAuditEntity<AccommodationTypeDetails, UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "type_id", nullable = false)
    private UUID id;
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
    private AccommodationTypeQuery accommodationType;

    public AccommodationTypeDetails(AccommodationTypeQuery accommodationType) {
        this.accommodationType = accommodationType;
    }
}
