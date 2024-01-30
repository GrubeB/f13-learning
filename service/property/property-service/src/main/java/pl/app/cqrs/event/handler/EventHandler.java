package pl.app.cqrs.event.handler;

/*
 * Object hold reference to method
 * */
public interface EventHandler<E> {
    boolean canHandle(Object event);

    void handle(E event);
}
