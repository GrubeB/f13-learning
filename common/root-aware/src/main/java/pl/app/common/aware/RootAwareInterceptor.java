package pl.app.common.aware;

import org.hibernate.CallbackException;
import org.hibernate.Interceptor;
import org.hibernate.type.Type;
import org.springframework.beans.factory.ObjectFactory;

import java.io.Serializable;
import java.util.Iterator;

public class RootAwareInterceptor implements Interceptor, Serializable {
    private final ObjectFactory<RootAwareService> rootAwareServiceObjectFactory;

    public RootAwareInterceptor(ObjectFactory<RootAwareService> rootAwareServiceObjectFactory) {
        this.rootAwareServiceObjectFactory = rootAwareServiceObjectFactory;
    }

    @Override
    public void onDelete(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        rootAwareServiceObjectFactory.getObject().awareEntityWasDeleted(entity);
        Interceptor.super.onDelete(entity, id, state, propertyNames, types);
    }

    @Override
    public boolean onFlushDirty(Object entity, Object id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) throws CallbackException {
        rootAwareServiceObjectFactory.getObject().awareEntityWasUpdated(entity);
        return Interceptor.super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }

    @Override
    public boolean onSave(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        rootAwareServiceObjectFactory.getObject().awareEntityWasCreated(entity);
        return Interceptor.super.onSave(entity, id, state, propertyNames, types);
    }

    @Override
    public void postFlush(Iterator<Object> entities) throws CallbackException {
        rootAwareServiceObjectFactory.getObject().updateRootEntities();
        Interceptor.super.postFlush(entities);
    }
}