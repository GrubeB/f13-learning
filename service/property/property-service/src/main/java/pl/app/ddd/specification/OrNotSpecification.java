package pl.app.ddd.specification;

public class OrNotSpecification<T> implements
        CompositeSpecification<T> {
    private final Specification<T> left;
    private final Specification<T> right;
    public OrNotSpecification(CompositeSpecification<T> left, Specification<T> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean isSatisfiedBy(T candidate) {
        return left.isSatisfiedBy(candidate) || !right.isSatisfiedBy(candidate);
    }
}
