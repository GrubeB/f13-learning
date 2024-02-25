package pl.app.property.accommodation_type.application.domain;

import pl.app.common.ddd.annotation.SpecificationAnnotation;
import pl.app.common.ddd.specification.CompositeSpecification;
import pl.app.common.ddd.specification.ConjunctionSpecification;
import pl.app.common.ddd.specification.NotSpecification;
import pl.app.common.ddd.specification.Specification;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AccommodationTypeSpecification {
    @SpecificationAnnotation
    public static class UniqueAccommodationName implements
            CompositeSpecification<Accommodation> {
        private final Set<Accommodation> accommodations;

        public UniqueAccommodationName(AccommodationType accommodationType) {
            this.accommodations = accommodationType.getAccommodations();
        }

        @Override
        public boolean isSatisfiedBy(Accommodation candidate) {
            List<Specification<Accommodation>> collect = accommodations.stream()
                    .map(accommodation -> new NotSpecification<>(new SimilarName(accommodation.getName())))
                    .collect(Collectors.toList());
            return new ConjunctionSpecification<>(collect).isSatisfiedBy(candidate);
        }
    }

    @SpecificationAnnotation
    public static class SimilarName implements
            CompositeSpecification<Accommodation> {
        private final String name;

        public SimilarName(String name) {
            this.name = name;
        }

        @Override
        public boolean isSatisfiedBy(Accommodation candidate) {
            return candidate.getName().equals(name);
        }
    }
}
