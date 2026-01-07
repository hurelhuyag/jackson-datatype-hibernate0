package io.github.hurelhuyag.jackson.hibernate;

import tools.jackson.core.JsonGenerator;
import jakarta.persistence.*;
import org.hibernate.Hibernate;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import tools.jackson.databind.ser.BeanPropertyWriter;
import tools.jackson.databind.ser.PropertyFilter;
import tools.jackson.databind.ser.PropertyWriter;

import java.lang.annotation.Annotation;
import java.util.function.Function;

public class LazyPropertyFilter implements PropertyFilter {

    @Override
    public void serializeAsProperty(Object pojo, JsonGenerator g, SerializationContext ctxt, PropertyWriter writer) throws Exception {
        var initialized = isPropertyInitialized((BeanPropertyWriter) writer, pojo);
        if (initialized) {
            writer.serializeAsProperty(pojo, g, ctxt);
        } else if (!g.canOmitProperties()) { // since 2.3
            writer.serializeAsOmittedProperty(pojo, g, ctxt);
        }
    }

    @Override
    public void serializeAsElement(Object elementValue, JsonGenerator g, SerializationContext ctxt, PropertyWriter writer) throws Exception {
        var initialized = isPropertyInitialized((BeanPropertyWriter) writer, elementValue);
        if (initialized) {
            writer.serializeAsElement(elementValue, g, ctxt);
        }
    }

    @Override
    public void depositSchemaProperty(PropertyWriter writer, JsonObjectFormatVisitor v, SerializationContext ctxt) {
        writer.depositSchemaProperty(v, ctxt);
    }

    @Override
    public LazyPropertyFilter snapshot() {
        return this;
    }

    public static boolean isPropertyInitialized(BeanPropertyWriter prop, Object bean) throws Exception {
        return Hibernate.isPropertyInitialized(bean, prop.getName())
            && isInitialized(prop, bean, ManyToOne.class, ManyToOne::fetch)
            && isInitialized(prop, bean, ElementCollection.class, ElementCollection::fetch)
            && isInitialized(prop, bean, OneToMany.class, OneToMany::fetch)
            && isInitialized(prop, bean, ManyToMany.class, ManyToMany::fetch)
            && isInitialized(prop, bean, OneToOne.class, OneToOne::fetch)
            && isInitialized(prop, bean, Basic.class, Basic::fetch);
    }

    public static <A extends Annotation> boolean isInitialized(
        BeanPropertyWriter prop, Object bean, Class<A> type, Function<A, FetchType> fetch) throws Exception {
        var ann = prop.getAnnotation(type);
        if (ann == null) {
            return true;
        }
        var fetchType = fetch.apply(ann);
        if (fetchType == FetchType.EAGER) {
            return true;
        }
        var value = prop.get(bean);
        return Hibernate.isInitialized(value);
    }
}
