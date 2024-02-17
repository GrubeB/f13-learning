package pl.app.common.model.audit;

import org.hibernate.HibernateException;
import org.hibernate.event.internal.DefaultFlushEntityEventListener;
import org.hibernate.event.spi.FlushEntityEvent;

import java.util.List;

public class IgnoreAuditFieldsOnDirtyCheckFlushEntityHibernateEventListener extends DefaultFlushEntityEventListener {
    private static final List<String> IGNORE_DIRTY_CHECK_PROPERTIES = List.of(
            AuditColumnName.CREATED_BY,
            AuditColumnName.CREATED_DATE,
            AuditColumnName.LAST_MODIFIED_BY,
            AuditColumnName.LAST_MODIFIED_DATE
    );

    @Override
    protected void dirtyCheck(final FlushEntityEvent event) throws HibernateException {
        super.dirtyCheck(event);
        removeIgnoredDirtyCheckProperties(event);
    }

    private void removeIgnoredDirtyCheckProperties(final FlushEntityEvent event) {
        String[] propertyNames = event.getEntityEntry().getPersister().getPropertyNames();
        int[] dirtyProperties = event.getDirtyProperties();
        if (dirtyProperties == null) return;

        var newDirtyProperties = new java.util.ArrayList<Integer>();
        Object[] propertyValues = event.getPropertyValues();

        for (int dirtyProperty : dirtyProperties) {
            if (!IGNORE_DIRTY_CHECK_PROPERTIES.contains(propertyNames[dirtyProperty])) { // property is not in ignore list
                newDirtyProperties.add(dirtyProperty);
            } else if (propertyValues[dirtyProperty] != null) { // property is in ignore list but is not null
                newDirtyProperties.add(dirtyProperty);
            }
        }
        int[] newDirtyPropertiesArray = newDirtyProperties.stream().mapToInt(i -> i).toArray();
        event.setDirtyProperties(newDirtyPropertiesArray.length > 0 ? newDirtyPropertiesArray : null);
    }

}
