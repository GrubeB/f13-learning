package pl.app.common.search_criteria;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.proxy.HibernateProxy;

import java.time.Instant;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "property_name", nullable = false)
    private String name;

    @Column(name = "check_in_from_time")
    private LocalTime checkInFromTime;
    @Column(name = "check_in_to_time")
    private LocalTime checkInToTime;

    @Column(name = "created_date")
    private Instant createdDate;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "property",
            orphanRemoval = true)
    private Set<PropertyAddress> addresses = new LinkedHashSet<>();

    public Property(String name, LocalTime checkInFromTime, LocalTime checkInToTime, Instant createdDate, Set<PropertyAddress> addresses) {
        this.name = name;
        this.checkInFromTime = checkInFromTime;
        this.checkInToTime = checkInToTime;
        this.createdDate = createdDate;
        this.setAddresses(addresses);
    }

    public void setAddresses(Set<PropertyAddress> addresses) {
        if (this.addresses != addresses) {
            this.addresses.forEach(p -> p.setProperty(null));
            this.addresses.clear();
            addresses.forEach(this::addAddresses);
        }
    }

    public void addAddresses(PropertyAddress address) {
        this.addresses.add(address);
        address.setProperty(this);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Property property = (Property) o;
        return getId() != null && Objects.equals(getId(), property.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
