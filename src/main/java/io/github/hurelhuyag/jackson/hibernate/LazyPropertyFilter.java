package io.github.hurelhuyag.jackson.hibernate;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.lang.annotation.Annotation;
import java.util.function.Function;

public class LazyPropertyFilter implements PropertyFilter {

    @Override
    public void serializeAsField(Object pojo, JsonGenerator gen, SerializerProvider prov, PropertyWriter writer) throws Exception {
        var initialized = isPropertyInitialized((BeanPropertyWriter) writer, pojo);
        if (initialized) {
            writer.serializeAsField(pojo, gen, prov);
        } else if (!gen.canOmitFields()) { // since 2.3
            writer.serializeAsOmittedField(pojo, gen, prov);
        }
    }

    @Override
    public void serializeAsElement(Object elementValue, JsonGenerator gen, SerializerProvider prov, PropertyWriter writer) throws Exception {
        var initialized = isPropertyInitialized((BeanPropertyWriter) writer, elementValue);
        if (initialized) {
            writer.serializeAsElement(elementValue, gen, prov);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void depositSchemaProperty(PropertyWriter writer, ObjectNode propertiesNode, SerializerProvider provider) throws JsonMappingException {
        writer.depositSchemaProperty(propertiesNode, provider);
    }

    @Override
    public void depositSchemaProperty(PropertyWriter writer, JsonObjectFormatVisitor objectVisitor, SerializerProvider provider) throws JsonMappingException {
        writer.depositSchemaProperty(objectVisitor, provider);
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
