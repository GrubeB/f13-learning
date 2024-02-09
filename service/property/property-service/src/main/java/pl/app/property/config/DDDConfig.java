package pl.app.property.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.app.cqrs.event.gateway.EventGateway;
import pl.app.ddd.event.DomainEventPublisher;
import pl.app.ddd.event.DomainEventPublisherFactory;
import pl.app.ddd.event.impl.AbstractDelayedDomainEventPublisher;

@Configuration
public class DDDConfig {
    @Bean
    DomainEventPublisher domainEventPublisher(EventGateway eventGateway) {
        return new DomainEventPublisher() {
            @Override
            public <C> void publish(C event) {
                eventGateway.publish(event);
            }
        };
    }

    @Bean
    DomainEventPublisherFactory domainEventPublisherFactory(EventGateway eventGateway) {
        return new DomainEventPublisherFactory() {
            @Override
            public DomainEventPublisher getEventPublisher() {
                return new AbstractDelayedDomainEventPublisher() {
                    @Override
                    public void publishDelayedEvents() {
                        getEvents().forEach(eventGateway::publish);
                        getEvents().clear();
                    }
                };
            }
        };
    }
}
