package com.github.shihyuho.jackson.databind;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.shihyuho.jackson.databind.autoconfigure.JacksonDynamicFilterAutoConfiguration;
import org.junit.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import static org.assertj.core.api.Assertions.assertThat;

public class JacksonDynamicFilterStarterTest {

  private static final String RESOLVER_CLASS_NAMES = "jackson.dynamic.filter.resolver.class-names";
  private static final String FAIL_FAST = "jackson.dynamic.filter.fail-fast";

  @Test
  public void testAutoconfigureInjectResolvers() {
    System.setProperty(RESOLVER_CLASS_NAMES, IncludeAllResolver.class.getName());

    try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
      context.register(JacksonDynamicFilterAutoConfiguration.class);
      context.register(ExcludeAllResolver.class);
      context.refresh();

      DynamicFilterResponseBodyAdvice advice = null;
      try {
        advice = context.getBean(DynamicFilterResponseBodyAdvice.class);
      } catch (Exception e) {
        assertThat(e).isExactlyInstanceOf(BeanCreationException.class);
      }

      assertThat(advice).isNotNull();
      assertThat(advice.resolvers).isNotNull().hasSize(4);
    }

    System.clearProperty(RESOLVER_CLASS_NAMES);
  }

  @Test
  public void testCustomizeConfiguration() {
    try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
      context.register(MyConfiguration.class);
      context.register(JacksonDynamicFilterAutoConfiguration.class);
      context.refresh();

      DynamicFilterResponseBodyAdvice advice = null;
      try {
        advice = context.getBean(DynamicFilterResponseBodyAdvice.class);
      } catch (Exception e) {
        assertThat(e).isExactlyInstanceOf(BeanCreationException.class);
      }

      assertThat(advice).isNotNull();
      assertThat(advice.resolvers).isNotNull().hasSize(2);

      MappingJackson2HttpMessageConverter converter = null;
      try {
        converter = context.getBean(MappingJackson2HttpMessageConverter.class);
      } catch (Exception e) {
        assertThat(e).isExactlyInstanceOf(BeanCreationException.class);
      }
      assertThat(converter).isNotNull();
      assertThat(converter.getObjectMapper().isEnabled(SerializationFeature.INDENT_OUTPUT))
          .isTrue();
    }
  }

  @Test
  public void testFailFast() {
    System.setProperty(RESOLVER_CLASS_NAMES, "com.github.shihyuho.doesnt.even.exist");
    System.setProperty(FAIL_FAST, "true");

    try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
      context.register(JacksonDynamicFilterAutoConfiguration.class);
      context.refresh();
    } catch (Exception e) {
      assertThat(e).isExactlyInstanceOf(BeanCreationException.class);
    }
    System.clearProperty(RESOLVER_CLASS_NAMES);
    System.clearProperty(FAIL_FAST);
  }
}
