package pl.app.common.cqrs.command.handler.impl;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.handler.CommandHandler;
import pl.app.common.cqrs.command.handler.CommandHandlerContext;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

public class SpringCommandHandlerContext implements
        CommandHandlerContext,
        ApplicationListener<ContextRefreshedEvent> {

    private final Set<CommandHandler<?, ?>> commandHandlers = new HashSet<>();
    private final ApplicationContext context;

    public SpringCommandHandlerContext(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void registerCommandHandling(CommandHandler<?, ?> commandHandler) {
        this.commandHandlers.add(commandHandler);
    }

    @Override
    public void registerCommandHandler(Object commandHandler) {
        if (Enhancer.isEnhanced(commandHandler.getClass())) {
            registerEnhancedCommandHandler(commandHandler);
        } else {
            registerNormalCommandHandler(commandHandler);
        }
    }

    public void registerNormalCommandHandler(Object commandHandler) {
        final Method[] classMethods = commandHandler.getClass().getMethods();
        Stream.of(classMethods)
                .filter(method -> Objects.nonNull(method.getAnnotation(CommandHandlingAnnotation.class)))
                .forEach(method -> {
                    final Class<?>[] methodParameterTypes = method.getParameterTypes();
                    final Class<?> returnType = method.getReturnType();
                    registerCommandHandling(new SimpleCommandHandler<>(commandHandler, method, returnType, methodParameterTypes[0]));
                });
    }

    public void registerEnhancedCommandHandler(Object handler) {
        final Method[] classMethods = handler.getClass().getMethods();
        final Method[] superClassMethods = handler.getClass().getSuperclass().getMethods();

        Stream.of(superClassMethods)
                .filter(method -> Objects.nonNull(method.getAnnotation(CommandHandlingAnnotation.class)))
                .forEach(supperClassMethod -> {
                    Arrays.stream(classMethods)
                            .filter(method -> equalsMethods(method, supperClassMethod))
                            .findFirst().ifPresent(originalMethod -> {
                                final Class<?>[] methodParameterTypes = originalMethod.getParameterTypes();
                                final Class<?> returnType = originalMethod.getReturnType();
                                registerCommandHandling(new SimpleCommandHandler<>(handler, originalMethod, returnType, methodParameterTypes[0]));
                            });

                });
    }


    private boolean equalsMethods(Method m1, Method m2) {
        return m1.getName().equals(m2.getName())
                && m1.getReturnType().equals(m2.getReturnType())
                && List.of(m1.getParameterTypes()).equals(List.of(m2.getParameterTypes()));
    }


    @Override
    public Set<CommandHandler<?, ?>> getHandlers() {
        return commandHandlers;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        commandHandlers.clear();
        final Map<String, Object> commandHandlers = context.getBeansWithAnnotation(CommandHandlerAnnotation.class);
        commandHandlers.values().forEach(this::registerCommandHandler);
    }
}
