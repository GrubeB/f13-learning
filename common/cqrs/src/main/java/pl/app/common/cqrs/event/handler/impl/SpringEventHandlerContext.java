package pl.app.common.cqrs.event.handler.impl;


import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import pl.app.common.cqrs.event.annotation.EventHandlerAnnotation;
import pl.app.common.cqrs.event.annotation.EventHandlingAnnotation;
import pl.app.common.cqrs.event.handler.EventHandler;
import pl.app.common.cqrs.event.handler.EventHandlerContext;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class SpringEventHandlerContext implements
        EventHandlerContext,
        ApplicationListener<ContextRefreshedEvent> {
    private final Set<EventHandler<?>> eventHandlers = new HashSet<>();
    private final ApplicationContext context;

    public SpringEventHandlerContext(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public Set<EventHandler<?>> getHandlers() {
        return eventHandlers;
    }

    @Override
    public void registerEventHandling(EventHandler<?> eventHandler) {
        this.eventHandlers.add(eventHandler);
    }

    @Override
    public void registerEventHandler(Object eventHandler) {
        final Method[] methods = eventHandler.getClass().getMethods();
        Stream.of(methods).forEach(method -> {
            EventHandlingAnnotation annotation = method.getAnnotation(EventHandlingAnnotation.class);
            if (Objects.nonNull(annotation)) {
                final Class<?>[] methodParameterTypes = method.getParameterTypes();
                this.registerEventHandling(new SimpleEventHandler<>(eventHandler, method, methodParameterTypes[0]));
            }
        });
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        eventHandlers.clear();
        final Map<String, Object> commandHandlers = context.getBeansWithAnnotation(EventHandlerAnnotation.class);
        commandHandlers.values().forEach(this::registerEventHandler);
    }
}
