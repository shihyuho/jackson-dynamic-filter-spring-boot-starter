package examples.filter_out_all_except_type_of;

import examples.Pojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("examples.filter_out_all_except_type_of")
public class Controller {

  @FilterOutAllExceptTypeOf(Pojo.class)
  @GetMapping("/examples.filter_out_all_except_type_of")
  public Pojo0 getPojo() {
    return Pojo0.random();
  }
}
