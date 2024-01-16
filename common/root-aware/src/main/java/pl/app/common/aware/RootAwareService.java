package pl.app.common.aware;

public interface RootAwareService {
    void awareEntityWasUpdated(Object object);

    void awareEntityWasDeleted(Object object);

    void awareEntityWasCreated(Object object);

    void updateRootEntities();
}
