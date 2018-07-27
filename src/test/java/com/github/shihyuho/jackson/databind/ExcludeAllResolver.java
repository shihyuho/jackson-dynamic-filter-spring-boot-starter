package com.github.shihyuho.jackson.databind;

import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.github.shihyuho.jackson.databind.resolver.DynamicFilterResolver;
import org.springframework.stereotype.Component;

@Component
public class ExcludeAllResolver extends DynamicFilterResolver<ExcludeAll> {

  @Override
  public PropertyFilter apply(ExcludeAll excludeAll) {
    return new SimpleBeanPropertyFilter() {
      @Override
      protected boolean include(BeanPropertyWriter writer) {
        return false;
      }

      @Override
      protected boolean include(PropertyWriter writer) {
        return false;
      }

      @Override
      protected boolean includeElement(Object elementValue) {
        return false;
      }
    };
  }
}
