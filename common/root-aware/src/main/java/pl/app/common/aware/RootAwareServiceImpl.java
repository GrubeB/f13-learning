package pl.app.common.aware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Transactional
public class RootAwareServiceImpl implements RootAwareService {
    private final Logger LOGGER = LoggerFactory.getLogger(RootAwareService.class);
    protected Set<Object> rootEntitiesToUpdate = ConcurrentHashMap.newKeySet();
    protected final Set<Object> updatedEntities = ConcurrentHashMap.newKeySet();
    protected final Set<Object> createdEntities = ConcurrentHashMap.newKeySet();
    protected final Set<Object> deletedEntities = ConcurrentHashMap.newKeySet();
    private final RootAwareServiceOperation rootAwareServiceOperation;

    public RootAwareServiceImpl(RootAwareServiceOperation rootAwareServiceOperation) {
        this.rootAwareServiceOperation = rootAwareServiceOperation;
    }

    @Override
    public void awareEntityWasUpdated(Object object) {
        if (Objects.isNull(object)) return;
        updatedEntities.add(object);
        if (object instanceof RootAware<?> rootAware) {
            addEntityToUpdate(rootAware.root());
        }
    }

    @Override
    public void awareEntityWasDeleted(Object object) {
        if (Objects.isNull(object)) return;
        deletedEntities.add(object);
        if (object instanceof RootAware<?> rootAware) {
            addEntityToUpdate(rootAware.root());
        }

    }

    @Override
    public void awareEntityWasCreated(Object object) {
        if (Objects.isNull(object)) return;
        createdEntities.add(object);
        if (object instanceof RootAware<?> rootAware) {
            addEntityToUpdate(rootAware.root());
        }
    }

    private void addEntityToUpdate(Object object) {
        if (Objects.isNull(object)) return;
        rootEntitiesToUpdate.add(object);
        LOGGER.trace("Added root entity to update " + object.getClass());
    }

    @Override
    public void updateRootEntities() {
        this.filterRootEntitiesToUpdate();
        if (!rootEntitiesToUpdate.isEmpty()) {
            LOGGER.debug("Updating root entities");
            rootEntitiesToUpdate.forEach(rootAwareServiceOperation::updateOperation);
            LOGGER.debug("Updated root entities");
            rootEntitiesToUpdate.clear();
        }
    }

    protected void filterRootEntitiesToUpdate() {
        this.rootEntitiesToUpdate = this.rootEntitiesToUpdate
                .stream()
                .filter(obj -> !(this.createdEntities.contains(obj) || this.updatedEntities.contains(obj)))
                .collect(Collectors.toSet());
    }
}
