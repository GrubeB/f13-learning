package pl.app.authorization.role.application.domain;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.authorization.permision.query.PermissionQueryService;
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
public class RoleFactory {
    private final DomainEventPublisherFactory domainEventPublisherFactory;
    private final PermissionQueryService permissionQueryService;

    public Role create(String name, List<String> permissionNames) {
        List<AggregateId> permissions = permissionQueryService.fetchByCriteria(new SearchCriteria(List.of(
                new SearchCriteriaItem("name", Operator.IN, permissionNames)
        )), AggregateId.class);
        return new Role(name, permissions, domainEventPublisherFactory.getEventPublisher());
    }
}
