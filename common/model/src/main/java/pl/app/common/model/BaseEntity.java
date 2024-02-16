package pl.app.common.model;

import jakarta.persistence.MappedSuperclass;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.Objects;

/**
 * An abstract class representing the basic entity.
 * Provides an overridden equals() and hashCode() method that compares objects based on their identifiers
 *
 * @param <ENTITY>  Entity type
 * @param <ID>      Type of entity identifier
 */
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
public abstract class BaseEntity<
        ENTITY extends Identity<ID>,
        ID extends Serializable
        > implements
        Identity<ID> {
    @Override
    @SuppressWarnings("unchecked")
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ENTITY that = (ENTITY) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
