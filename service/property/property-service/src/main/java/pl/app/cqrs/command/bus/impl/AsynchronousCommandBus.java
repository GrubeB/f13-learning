package pl.app.cqrs.command.bus.impl;

import pl.app.cqrs.command.bus.CommandBus;
import pl.app.cqrs.command.handler.CommandHandler;
import pl.app.cqrs.command.handler.CommandHandlerProvider;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsynchronousCommandBus implements
        CommandBus {
    private final CommandHandlerProvider commandHandlerProvider;
    private final Executor executor = Executors.newFixedThreadPool(10);

    public AsynchronousCommandBus(CommandHandlerProvider commandHandlerProvider) {
        this.commandHandlerProvider = commandHandlerProvider;
    }

    @Override
    public <R, C> CompletableFuture<R> dispatch(C command) {
        CommandHandler<R, C> handler = commandHandlerProvider.getHandler(command);
        return CompletableFuture.supplyAsync(() -> handler.handle(command), executor);
    }
}
