package examples.custom_annotation;

import examples.Pojo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("examples.custom_annotation")
public class Controller {

  @WithoutAuditingFields
  @GetMapping("/examples.custom_annotation")
  public Pojo getPojo() {
    return Pojo.random();
  }
}
