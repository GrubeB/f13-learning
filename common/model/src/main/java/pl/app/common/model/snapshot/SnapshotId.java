package pl.app.common.model.snapshot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.common.model.Identity;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"owner"})
public class SnapshotId<ENTITY extends Identity<?>> implements
        Serializable {
    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private ENTITY owner;

    @Column(name = "snapshot_number")
    private Long snapshotNumber;

    public Object getOwnerId() {
        return owner.getId();
    }
}