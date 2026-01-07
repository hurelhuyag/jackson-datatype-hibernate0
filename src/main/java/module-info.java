module jackson.datatype.hibernate {

    requires tools.jackson.databind;

    requires jakarta.persistence;

    requires org.hibernate.orm.core;

    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.boot.jackson;

    exports io.github.hurelhuyag.jackson.hibernate;
}