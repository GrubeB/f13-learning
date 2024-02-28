package pl.app.learning.group.application.domain.snapshot;


import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.BaseJpaSnapshotDomainEntity;
import pl.app.common.ddd.annotation.EntityAnnotation;
import pl.app.learning.group.application.domain.*;

import java.util.LinkedHashSet;
import java.util.Set;

@EntityAnnotation
@Entity
@Getter
@Table(name = "t_group_snapshot")
public class GroupSnapshot extends BaseJpaSnapshotDomainEntity<Group, GroupSnapshot> {
    // TODO add rest of the fields
    public GroupSnapshot() {
        super();
    }
}

