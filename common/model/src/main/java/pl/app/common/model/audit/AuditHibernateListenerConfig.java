package pl.app.common.model.audit;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

import java.util.Objects;


public class AuditHibernateListenerConfig {
    @Bean
    HibernateIgnoreAuditFieldsOnDirtyCheckFlushEntityEventListenerConfigurator hibernateIgnoreAuditFieldsOnDirtyCheckFlushEntityEventListenerConfigurator() {
        return new HibernateIgnoreAuditFieldsOnDirtyCheckFlushEntityEventListenerConfigurator();
    }

    public static class HibernateIgnoreAuditFieldsOnDirtyCheckFlushEntityEventListenerConfigurator implements ApplicationListener<ApplicationReadyEvent> {
        @Override
        public void onApplicationEvent(ApplicationReadyEvent event) {
            EntityManagerFactory entityManagerFactory = event.getApplicationContext().getBean(EntityManagerFactory.class);
            SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
            ServiceRegistryImplementor serviceRegistry = sessionFactory.getServiceRegistry();
            EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
            if (Objects.nonNull(registry)) {
                registry.getEventListenerGroup(EventType.FLUSH_ENTITY).clearListeners();
                registry.getEventListenerGroup(EventType.FLUSH_ENTITY).appendListener(new IgnoreAuditFieldsOnDirtyCheckFlushEntityHibernateEventListener());
            }
        }
    }
}
