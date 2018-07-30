package examples.filter_out_all_except_type_of;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.github.shihyuho.jackson.databind.resolver.DynamicFilterResolver;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class FilterOutAllExceptTypeOfResolver
    extends DynamicFilterResolver<FilterOutAllExceptTypeOf> {
  @Override
  public PropertyFilter apply(FilterOutAllExceptTypeOf annotation) {
    return new SimpleBeanPropertyFilter() {
      @Override
      public void serializeAsField(
          Object pojo, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer)
          throws Exception {
        if (include(writer)) {
          if (Stream.of(annotation.value())
              .anyMatch(clz -> clz.isInstance(pojo) || writer.getType().isTypeOrSubTypeOf(clz))) {
            writer.serializeAsField(pojo, jgen, provider);
          }
        } else if (!jgen.canOmitFields()) { // since 2.3
          writer.serializeAsOmittedField(pojo, jgen, provider);
        }
      }
    };
  }
}
