package io.github.hurelhuyag.jackson.hibernate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

public class MyAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner =
            new ApplicationContextRunner()
                    .withConfiguration(AutoConfigurations.of(LazyPropertyConfiguration.class));

    @Test
    void myServiceBeanIsCreated() {
        contextRunner.run(context -> {
            assertThat(context).hasSingleBean(LazyPropertyConfiguration.class);
        });
    }
}