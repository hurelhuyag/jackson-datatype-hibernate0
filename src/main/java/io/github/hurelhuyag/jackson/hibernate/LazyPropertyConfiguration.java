package io.github.hurelhuyag.jackson.hibernate;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import tools.jackson.databind.ser.std.SimpleFilterProvider;

import java.util.Map;

@AutoConfiguration
@ConditionalOnClass({JsonMapperBuilderCustomizer.class})
public class LazyPropertyConfiguration {

    private static final String LAZY_PROPERTY_FILTER = EntityAnnotationIntrospector.LAZY_PROPERTY_FILTER;

    @Bean
    JsonMapperBuilderCustomizer hibernateLazyPropertySupportForJackson() {
        return builder -> builder
                .addModule(new EntityAnnotationIntrospectorModule())
                .filterProvider(new SimpleFilterProvider(Map.of(LAZY_PROPERTY_FILTER, new LazyPropertyFilter())));
    }
}
