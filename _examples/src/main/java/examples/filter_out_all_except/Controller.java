package examples.filter_out_all_except;

import com.github.shihyuho.jackson.databind.FilterOutAllExcept;
import examples.Pojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("examples.examples.filter_out_all_except")
public class Controller {

  @FilterOutAllExcept({"id", "createdBy", "createdTime", "modifiedBy", "modifiedTime"})
  @GetMapping("/examples.filter_out_all_except")
  public Pojo getPojo() {
    return Pojo.random();
  }
}
