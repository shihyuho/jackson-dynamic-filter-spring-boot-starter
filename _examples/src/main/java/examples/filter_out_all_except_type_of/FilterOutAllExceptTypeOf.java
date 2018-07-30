package examples.filter_out_all_except_type_of;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FilterOutAllExceptTypeOf {

  Class<?>[] value();
}
