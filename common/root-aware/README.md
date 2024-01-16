# Root Aware

## Table of Contents

* [General Info](#general-information)
* [Usage](#usage)

## General Information

Jar contains classes to aware JPA Entities after their children have been changed, such as: created, updated or deleted.

## Usage

### Configuration

1. There is a need to add bean to Spring container of type [RootAwareServiceOperation.java](src%2Fmain%2Fjava%2Fpl%2Fapp%2Fcommon%2Faware%2FRootAwareServiceOperation.java). 
Example configuration, which update audit fields: 

```java
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import pl.app.common.aware.RootAwareServiceOperation;
import pl.app.common.audit.Audit;

import java.time.Instant;

@Configuration
@Import(pl.app.common.aware.RootAwareConfig.class)
public class RootAwareConfig {
    @Bean
    public RootAwareServiceOperation rootAwareServiceOperation(EntityManager entityManager, AuditorAware<?> auditorAware) {
        return new RootAwareServiceOperation() {
            @Override
            public void updateOperation(Object root) {
                if (root instanceof Audit) {
                    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                    CriteriaUpdate<?> criteriaUpdate = cb.createCriteriaUpdate(root.getClass());
                    criteriaUpdate.set("lastModifiedDate", Instant.now());
                    criteriaUpdate.set("lastModifiedBy", auditorAware.getCurrentAuditor().orElse(null));
                    entityManager.createQuery(criteriaUpdate).executeUpdate();
                }
            }
        };
    }
}
```

### Entity:
1. Parent entity is unchanged, for example:
```java
@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_chapter")
public class ChapterEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "chapter_id", nullable = false)
    private UUID chapterId;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "chapter",
            orphanRemoval = true)
    @Builder.Default
    private Set<ReferenceEntity> references = new LinkedHashSet<>();
    // ...
}

```

2. Child entity implement [RootAware.java](src%2Fmain%2Fjava%2Fpl%2Fapp%2Fcommon%2Faware%2FRootAware.java) interface:

```java
@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_chapter_reference")
public class ReferenceEntity implements
        RootAware<ChapterEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "reference_id", nullable = false)
    private UUID referenceId;

    @ManyToOne
    @JoinColumn(name = "chapter_id")
    @ToString.Exclude
    @JsonIgnore
    private ChapterEntity chapter;

    @Override
    public ChapterEntity root() {
        return chapter;
    }

    // ...
}

```