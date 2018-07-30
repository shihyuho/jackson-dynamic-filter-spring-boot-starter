package examples;

import lombok.Data;

import java.util.Random;

@Data
public class Pojo {

  private Long id;
  private String createdBy;
  private String modifiedBy;

  private int field1;
  private int field2;
  private int field3;

  public static Pojo random() {
    Random r = new Random();
    Pojo p = new Pojo();
    p.id = r.nextLong();
    p.createdBy = Thread.currentThread().getName();
    p.modifiedBy = p.createdBy;
    p.field1 = r.nextInt(99);
    p.field2 = r.nextInt(99);
    p.field3 = r.nextInt(99);
    return p;
  }
}
