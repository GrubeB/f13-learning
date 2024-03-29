package pl.app.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.app.common.model.audit.Audit;
import pl.app.common.model.audit.AuditColumnName;

import java.io.Serializable;
import java.time.Instant;
/**
 * An abstract class representing the basic entity with auditing.
 *
 * @param <ENTITY>  Entity type
 * @param <ID>      Type of entity identifier
 */
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@SuperBuilder
@JsonIgnoreProperties(value = {"createdBy", "createdDate","lastModifiedBy","lastModifiedDate"})
public abstract class BaseAuditEntity<
        ENTITY extends Identity<ID>,
        ID extends Serializable
        > extends BaseEntity<ENTITY, ID> implements
        Audit {
    @CreatedBy
    @Column(name = AuditColumnName.CREATED_BY, nullable = false, updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(name = AuditColumnName.CREATED_DATE, nullable = false, updatable = false)
    private Instant createdDate;

    @LastModifiedBy
    @Column(name = AuditColumnName.LAST_MODIFIED_BY)
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = AuditColumnName.LAST_MODIFIED_DATE)
    private Instant lastModifiedDate;

    public BaseAuditEntity() {
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
