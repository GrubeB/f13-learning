package pl.app.common.cqrs.command.bus;

import java.util.concurrent.CompletableFuture;

public interface CommandBus {
    <R, C> CompletableFuture<R> dispatch(C command);
}
