package pl.app.property.accommodation_type.application.domain;

import pl.app.ddd.annotation.SpecificationAnnotation;
import pl.app.ddd.specification.CompositeSpecification;
import pl.app.ddd.specification.ConjunctionSpecification;
import pl.app.ddd.specification.NotSpecification;
import pl.app.ddd.specification.Specification;

import java.util.List;
import java.util.stream.Collectors;

public class AccommodationTypeSpecification{
    @SpecificationAnnotation
    public static class UniqueAccommodationName implements
            CompositeSpecification<Accommodation> {
        private final List<Accommodation> accommodations;
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