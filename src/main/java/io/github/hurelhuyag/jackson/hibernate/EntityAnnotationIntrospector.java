package io.github.hurelhuyag.jackson.hibernate;

import jakarta.persistence.Entity;
import tools.jackson.databind.cfg.MapperConfig;
import tools.jackson.databind.introspect.Annotated;
import tools.jackson.databind.introspect.JacksonAnnotationIntrospector;

public class EntityAnnotationIntrospector extends JacksonAnnotationIntrospector {

    public static final String LAZY_PROPERTY_FILTER = "lazyPropertyFilter";

    @Override
    public Object findFilterId(MapperConfig<?> config, Annotated a) {
        var isEntity = a.hasAnnotation(Entity.class);
        if (isEntity) {
            return LAZY_PROPERTY_FILTER;
        }
        return super.findFilterId(config, a);
    }
}
