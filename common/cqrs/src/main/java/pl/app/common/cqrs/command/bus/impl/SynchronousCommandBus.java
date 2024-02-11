package pl.app.common.cqrs.command.bus.impl;

import pl.app.common.cqrs.command.bus.CommandBus;
import pl.app.common.cqrs.command.handler.CommandHandler;
import pl.app.common.cqrs.command.handler.CommandHandlerProvider;

import java.util.concurrent.CompletableFuture;

public class SynchronousCommandBus implements
        CommandBus {
    private final CommandHandlerProvider commandHandlerProvider;

    public SynchronousCommandBus(CommandHandlerProvider commandHandlerProvider) {
        this.commandHandlerProvider = commandHandlerProvider;
    }

    @Override
    public <R, C> CompletableFuture<R> dispatch(C command) {
        CommandHandler<R, C> handler = commandHandlerProvider.getHandler(command);
        final R result = handler.handle(command);
        CompletableFuture<R> objectCompletableFuture = new CompletableFuture<>();
        objectCompletableFuture.obtrudeValue(result);
        return objectCompletableFuture;
    }
}
