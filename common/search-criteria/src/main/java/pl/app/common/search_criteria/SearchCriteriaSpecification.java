package pl.app.common.search_criteria;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public class SearchCriteriaSpecification<T> implements
        Specification<T> {
    private final transient SearchCriteria searchCriteria;

    public SearchCriteriaSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return getSpecification().toPredicate(root, query, cb);
    }

    public Specification<T> getSpecification() {
        Specification<T> specification = Specification.where((Specification<T>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.literal(Boolean.TRUE), Boolean.TRUE)); // always true
        if (Objects.nonNull(this.searchCriteria)) {
            for (SearchCriteriaItem criteriaItem : this.searchCriteria.getCriteria()) {
                if (criteriaItem.getConditionOperator() == null) {
                    specification = specification.and(criteriaItem.getOperator().build(criteriaItem));
                } else if (ConditionOperator.AND.equals(criteriaItem.getConditionOperator())) {
                    specification = specification.and(criteriaItem.getOperator().build(criteriaItem));
                } else if (ConditionOperator.OR.equals(criteriaItem.getConditionOperator())) {
                    specification = specification.or(criteriaItem.getOperator().build(criteriaItem));
                }
            }
        }
        return specification;
    }
}
