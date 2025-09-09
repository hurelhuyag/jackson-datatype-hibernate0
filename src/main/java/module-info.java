module jackson.datatype.hibernate {

    requires com.fasterxml.jackson.databind;

    requires jakarta.persistence;

    requires org.hibernate.orm.core;

    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.web;

    exports io.github.hurelhuyag.jackson.hibernate;
}