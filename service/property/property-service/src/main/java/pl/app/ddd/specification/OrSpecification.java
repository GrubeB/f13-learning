package pl.app.ddd.specification;

public class OrSpecification<T> implements
        CompositeSpecification<T> {
    private final Specification<T> left;
    private final Specification<T> right;
    public OrSpecification(CompositeSpecification<T> left, Specification<T> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean isSatisfiedBy(T candidate) {
        return left.isSatisfiedBy(candidate) || right.isSatisfiedBy(candidate);
    }
}
