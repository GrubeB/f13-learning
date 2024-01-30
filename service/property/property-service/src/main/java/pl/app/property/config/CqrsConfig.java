package pl.app.property.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.app.cqrs.command.bus.impl.AsynchronousCommandBus;
import pl.app.cqrs.command.bus.impl.SynchronousCommandBus;
import pl.app.cqrs.command.gateway.CommandGateway;
import pl.app.cqrs.command.gateway.impl.DefaultCommandGateway;
import pl.app.cqrs.command.handler.CommandHandlerContext;
import pl.app.cqrs.command.handler.CommandHandlerProvider;
import pl.app.cqrs.command.handler.impl.SimpleCommandHandlerProvider;
import pl.app.cqrs.command.handler.impl.SpringCommandHandlerContext;
import pl.app.cqrs.event.bus.EventBus;
import pl.app.cqrs.event.bus.impl.AsynchronousEventBus;
import pl.app.cqrs.event.gateway.EventGateway;
import pl.app.cqrs.event.gateway.impl.DefaultEventGateway;
import pl.app.cqrs.event.handler.EventHandlerContext;
import pl.app.cqrs.event.handler.EventHandlerProvider;
import pl.app.cqrs.event.handler.impl.SimpleEventHandlerProvider;
import pl.app.cqrs.event.handler.impl.SpringEventHandlerContext;

@Configuration
public class CqrsConfig {
    @Bean
    CommandGateway commandGateway(CommandHandlerProvider commandHandlerProvider) {
        return new DefaultCommandGateway(
                new SynchronousCommandBus(commandHandlerProvider),
                new AsynchronousCommandBus(commandHandlerProvider)
        );
    }

    @Bean
    CommandHandlerProvider commandHandlerProvider(CommandHandlerContext commandHandlerContext) {
        return new SimpleCommandHandlerProvider(commandHandlerContext);
    }

    @Bean
    CommandHandlerContext commandHandlerContext(ApplicationContext context) {
        return new SpringCommandHandlerContext(context);
    }

    @Bean
    EventGateway eventGateway(EventBus eventBus) {
        return new DefaultEventGateway(eventBus);
    }

    @Bean
    EventBus eventBus(EventHandlerProvider eventHandlerProvider) {
        return new AsynchronousEventBus(eventHandlerProvider);
    }

    @Bean
    EventHandlerProvider eventHandlerProvider(EventHandlerContext eventHandlerContext) {
        return new SimpleEventHandlerProvider(eventHandlerContext);
    }

    @Bean
    EventHandlerContext eventHandlerContext(ApplicationContext context) {
        return new SpringEventHandlerContext(context);
    }
}
