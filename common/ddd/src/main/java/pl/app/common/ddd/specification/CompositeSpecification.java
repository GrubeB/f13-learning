package pl.app.common.ddd.specification;

public interface CompositeSpecification<T> extends
        Specification<T> {
    boolean isSatisfiedBy(T candidate);

    @Override
    default Specification<T> and(Specification<T> other) {
        return new AndSpecification<T>(this, other);
    }

    @Override
    default Specification<T> andNot(Specification<T> other) {
        return new AndNotSpecification<T>(this, other);
    }

    @Override
    default Specification<T> or(Specification<T> other) {
        return new OrSpecification<T>(this, other);
    }

    @Override
    default Specification<T> orNot(Specification<T> other) {
        return new OrNotSpecification<T>(this, other);
    }

    @Override
    default Specification<T> not() {
        return new NotSpecification<T>(this);
    }
}
