package pl.app.common.search_criteria;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
 class PropertyService {
    private final PropertyRepository repository;

    public List<Property> fetchByCriteria(SearchCriteria criteria) {
        Specification<Property> specification = new SearchCriteriaSpecification<>(criteria);
        return repository.findAll(specification);
    }
}
