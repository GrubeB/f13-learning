package pl.app.learning.path.query.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainAggregateRoot;
import pl.app.common.ddd.annotation.AggregateRootAnnotation;
import pl.app.common.model.BaseAuditEntity;
import pl.app.learning.path.application.domain.ItemEntityType;
import pl.app.learning.path.application.domain.PathException;
import pl.app.learning.path.application.domain.PathSpecification;
import pl.app.learning.path.application.domain.PathStatus;
import pl.app.learning.topic.query.model.TopicQuery;

import java.util.*;
import java.util.stream.Collectors;

@Immutable
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_path")
public class PathQuery extends BaseAuditEntity<PathQuery, UUID> {
    @Id
    private UUID id;
    @Column(name = "path_name")
    private String name;
    @Column(name = "path_content")
    private String content;
    @Enumerated(EnumType.STRING)
    @Column(name = "path_status")
    private PathStatus status;
    @OneToMany(mappedBy = "path", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private final Set<PathHasCategoryQuery> categories = new LinkedHashSet<>();
    @OneToMany(mappedBy = "path", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private final Set<PathItemQuery> items = new LinkedHashSet<>();
}

