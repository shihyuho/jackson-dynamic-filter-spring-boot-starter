package examples.filter_out_all_except_type_of;

import examples.Pojo;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Random;

@Data
public class Pojo0 {

  private Long id;
  private String createdBy;
  private String modifiedBy;

  private Pojo pojo;

  private int field0;

  public static Pojo0 random() {
    Random r = new Random();
    Pojo0 p = new Pojo0();
    p.id = r.nextLong();
    p.createdBy = Thread.currentThread().getName();
    p.modifiedBy = p.createdBy;
    p.pojo = Pojo.random();
    p.field0 = r.nextInt(99);
    return p;
  }
}
