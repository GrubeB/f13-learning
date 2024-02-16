package pl.app.common.model;

import jakarta.persistence.MappedSuperclass;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.app.common.audit.Audit;

import java.io.Serializable;

@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
public abstract class BaseAuditEntity<ID extends Serializable> extends Audit implements
        Identity<ID> {
}
