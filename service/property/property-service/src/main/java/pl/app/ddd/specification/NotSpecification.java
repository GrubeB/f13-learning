package pl.app.ddd.specification;

public class NotSpecification<T> implements
        CompositeSpecification<T> {
    private final Specification<T> other;

    public NotSpecification(CompositeSpecification<T> other) {
        this.other = other;
    }

    @Override
    public boolean isSatisfiedBy(T candidate) {
        return !other.isSatisfiedBy(candidate);
    }
}
