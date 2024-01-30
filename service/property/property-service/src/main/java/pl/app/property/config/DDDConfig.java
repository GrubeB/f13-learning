package pl.app.property.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.app.cqrs.event.gateway.EventGateway;
import pl.app.ddd.event.DomainEventPublisher;

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
}
