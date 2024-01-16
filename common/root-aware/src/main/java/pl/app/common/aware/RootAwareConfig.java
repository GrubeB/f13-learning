package pl.app.common.aware;

import org.hibernate.Interceptor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

@Configuration
@Import(TransactionConfig.class)
public class RootAwareConfig {

    @Bean
    @Scope(scopeName = "transaction")
    public RootAwareService rootAwareService(RootAwareServiceOperation rootAwareServiceOperation) {
        return new RootAwareServiceImpl(rootAwareServiceOperation);
    }

    @Bean
    public Interceptor hibernateInterceptor(@Qualifier("rootAwareService") ObjectFactory<RootAwareService> rootAwareServiceObjectFactory) {
        return new RootAwareInterceptor(rootAwareServiceObjectFactory);
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(Interceptor hibernateInterceptor) {
        return hibernateProperties -> {
            hibernateProperties.put("hibernate.session_factory.interceptor", hibernateInterceptor);
        };
    }
}
