package pl.app.authorization.user.application.domain;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.app.authorization.role.query.RoleQueryService;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.FactoryAnnotation;
import pl.app.common.ddd.event.DomainEventPublisherFactory;
import pl.app.common.search_criteria.Operator;
import pl.app.common.search_criteria.SearchCriteria;
import pl.app.common.search_criteria.SearchCriteriaItem;

import java.util.List;

@FactoryAnnotation
@Component
@RequiredArgsConstructor
public class UserFactory {
    private final DomainEventPublisherFactory domainEventPublisherFactory;
    private final RoleQueryService roleQueryService;
    private final PasswordEncoder passwordEncoder;

    public User create(String email, String rawPassword, List<String> roleNames) {
        List<AggregateId> roles = roleQueryService.fetchByCriteria(new SearchCriteria(List.of(
                new SearchCriteriaItem("name", Operator.IN, roleNames)
        )), AggregateId.class);
        return new User(email, rawPassword, roles, domainEventPublisherFactory.getEventPublisher(), passwordEncoder);
    }
}
