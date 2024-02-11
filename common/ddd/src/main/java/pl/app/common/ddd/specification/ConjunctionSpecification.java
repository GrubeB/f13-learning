package pl.app.common.ddd.specification;

import java.util.Collection;
import java.util.stream.Stream;

public class ConjunctionSpecification<T> implements
        CompositeSpecification<T> {
    private final Specification<T>[] specifications;

    public ConjunctionSpecification(Specification<T>... specifications) {
        this.specifications = specifications;
    }
    @SuppressWarnings("unchecked")
    public ConjunctionSpecification(Collection<Specification<T>> specifications) {
        this.specifications = specifications.toArray(new Specification[specifications.size()]);
    }
    @Override
    public boolean isSatisfiedBy(T candidate) {
        return Stream.of(specifications)
                .allMatch(specification -> specification.isSatisfiedBy(candidate));
    }
}
