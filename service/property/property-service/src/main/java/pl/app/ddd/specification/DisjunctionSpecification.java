package pl.app.ddd.specification;

import java.util.stream.Stream;

public class DisjunctionSpecification<T> implements
        CompositeSpecification<T> {
    private final Specification<T>[] specifications;

    public DisjunctionSpecification(Specification<T>... specifications) {
        this.specifications = specifications;
    }

    @Override
    public boolean isSatisfiedBy(T candidate) {
        return Stream.of(specifications)
                .anyMatch(specification -> specification.isSatisfiedBy(candidate));
    }
}
