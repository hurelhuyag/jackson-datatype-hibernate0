package io.github.hurelhuyag.jackson.hibernate;

import tools.jackson.databind.module.SimpleModule;

public class EntityAnnotationIntrospectorModule extends SimpleModule {

    @Override
    public void setupModule(SetupContext context) {
        var ai = new EntityAnnotationIntrospector();
        context.insertAnnotationIntrospector(ai);
    }
}
