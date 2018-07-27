package com.github.shihyuho.jackson.databind.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "jackson.dynamic.filter")
public class JacksonDynamicFilterProperties {

  /**
   * Application will halt if any exception occurred while initialing jackson-dynamic-filter;
   * otherwise just write a error log.
   */
  private boolean failFast;

  private Resolver resolver = new Resolver();

  @Getter
  @Setter
  public static class Resolver {
    private String[] classNames;
  }
}
