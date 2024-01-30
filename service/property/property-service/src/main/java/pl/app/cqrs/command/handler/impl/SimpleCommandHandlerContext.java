package pl.app.cqrs.command.handler.impl;

import pl.app.cqrs.command.handler.CommandHandler;
import pl.app.cqrs.command.handler.CommandHandlerContext;
import pl.app.cqrs.command.annotation.CommandHandlingAnnotation;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class SimpleCommandHandlerContext implements
        CommandHandlerContext {
    private final Set<CommandHandler<?, ?>> commandHandlers = new HashSet<>();

    @Override
    public Set<CommandHandler<?, ?>> getHandlers() {
        return commandHandlers;
    }

    @Override
    public void registerCommandHandling(CommandHandler<?, ?> commandHandler) {
        this.commandHandlers.add(commandHandler);
    }

    @Override
    public void registerCommandHandler(Object commandHandler) {
        final Method[] methods = commandHandler.getClass().getMethods();
        Stream.of(methods).forEach(method -> {
            CommandHandlingAnnotation annotation = method.getAnnotation(CommandHandlingAnnotation.class);
            if (Objects.nonNull(annotation)) {
                final Class<?>[] methodParameterTypes = method.getParameterTypes();
                final Class<?> returnType = method.getReturnType();
                registerCommandHandling(new SimpleCommandHandler<>(commandHandler, method, returnType, methodParameterTypes[0]));
            }
        });
    }
}
