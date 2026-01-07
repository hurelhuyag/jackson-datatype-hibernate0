package io.github.hurelhuyag.jackson.hibernate;

import org.hibernate.engine.spi.ManagedEntity;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import tools.jackson.databind.ser.std.SimpleFilterProvider;

import java.util.Map;

@AutoConfiguration
@ConditionalOnClass({JsonMapperBuilderCustomizer.class})
public class LazyPropertyConfiguration {

    @Bean
    JsonMapperBuilderCustomizer hibernateLazyPropertySupportForJackson() {
        return builder -> builder
                .addMixIn(ManagedEntity.class, LazyPropertyFilterMixin.class)
                .addMixIn(HibernateProxy.class, LazyPropertyFilterMixin.class)
                .filterProvider(new SimpleFilterProvider(Map.of("lazyPropertyFilter", new LazyPropertyFilter())));
    }
}
