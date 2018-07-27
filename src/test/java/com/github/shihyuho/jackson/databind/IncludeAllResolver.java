package com.github.shihyuho.jackson.databind;

import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.github.shihyuho.jackson.databind.resolver.DynamicFilterResolver;
import org.springframework.stereotype.Component;

public class IncludeAllResolver extends DynamicFilterResolver<IncludeAll> {

  @Override
  public PropertyFilter apply(IncludeAll includeAll) {
    return new SimpleBeanPropertyFilter() {
      @Override
      protected boolean include(BeanPropertyWriter writer) {
        return true;
      }

      @Override
      protected boolean include(PropertyWriter writer) {
        return true;
      }

      @Override
      protected boolean includeElement(Object elementValue) {
        return true;
      }
    };
  }
}
