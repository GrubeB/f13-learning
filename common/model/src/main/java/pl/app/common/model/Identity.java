package pl.app.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

public interface Identity<ID extends Serializable> extends
        Persistable<ID> {
    ID getId();

    void setId(ID id);

    @Override
    @JsonIgnore
    default boolean isNew() {
        return null == this.getId();
    }
}

