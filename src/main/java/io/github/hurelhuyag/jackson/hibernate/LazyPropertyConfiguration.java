package io.github.hurelhuyag.jackson.hibernate;

import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.hibernate.engine.spi.ManagedEntity;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import java.util.Map;

@AutoConfiguration
@ConditionalOnClass({Jackson2ObjectMapperBuilderCustomizer.class})
public class LazyPropertyConfiguration {

    @Bean
    Jackson2ObjectMapperBuilderCustomizer hibernateLazyPropertySupportForJackson() {
        return builder -> builder
                .mixIn(ManagedEntity.class, LazyPropertyFilterMixin.class)
                .mixIn(HibernateProxy.class, LazyPropertyFilterMixin.class)
                .filters(new SimpleFilterProvider(Map.of("lazyPropertyFilter", new LazyPropertyFilter())));
    }
}
