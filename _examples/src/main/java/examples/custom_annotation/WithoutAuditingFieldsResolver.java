package examples.custom_annotation;

import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.github.shihyuho.jackson.databind.resolver.DynamicFilterResolver;
import org.springframework.stereotype.Component;

@Component
public class WithoutAuditingFieldsResolver extends DynamicFilterResolver<WithoutAuditingFields> {

  @Override
  public PropertyFilter apply(WithoutAuditingFields annotation) {
    return SimpleBeanPropertyFilter.serializeAllExcept(
        "id", "createdBy", "createdTime", "modifiedBy", "modifiedTime");
  }
}
