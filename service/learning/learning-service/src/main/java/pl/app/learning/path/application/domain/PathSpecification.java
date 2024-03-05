package pl.app.learning.path.application.domain;

import pl.app.common.ddd.annotation.SpecificationAnnotation;
import pl.app.common.ddd.specification.CompositeSpecification;
import pl.app.common.ddd.specification.ConjunctionSpecification;
import pl.app.common.ddd.specification.NotSpecification;
import pl.app.common.ddd.specification.Specification;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class PathSpecification {
    @SpecificationAnnotation
    public static class UniqueEntitiesInPathItems implements
            CompositeSpecification<PathItem> {
        private final Set<PathItem> items;

        public UniqueEntitiesInPathItems(Set<PathItem> items) {
            this.items = items;
        }

        @Override
        public boolean isSatisfiedBy(PathItem candidate) {
            List<Specification<PathItem>> collect = items.stream()
                    .map(item -> new NotSpecification<>(new SimilarEntityInPathItem(item.getEntityType(), item.getEntity().getId())))
                    .collect(Collectors.toList());
            return new ConjunctionSpecification<>(collect).isSatisfiedBy(candidate);
        }
    }

    @SpecificationAnnotation
    public static class SimilarEntityInPathItem implements
            CompositeSpecification<PathItem> {
        private final ItemEntityType entityType;
        private final UUID entityId;

        public SimilarEntityInPathItem(ItemEntityType entityType, UUID entityId) {
            this.entityType = entityType;
            this.entityId = entityId;
        }

        @Override
        public boolean isSatisfiedBy(PathItem candidate) {
            return candidate.getEntityType().equals(entityType)
                    && candidate.getEntity().getId().equals(entityId);
        }
    }
}
