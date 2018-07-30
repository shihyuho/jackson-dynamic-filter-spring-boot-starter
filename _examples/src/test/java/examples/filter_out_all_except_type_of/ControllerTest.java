package examples.filter_out_all_except_type_of;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ControllerTest {

  @Autowired private WebApplicationContext wac;
  private MockMvc mockMvc;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test
  public void testGetPojo() throws Exception {
    MvcResult r =
        mockMvc
            .perform(get("/examples.filter_out_all_except_type_of"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.id").doesNotExist())
            .andExpect(jsonPath("$.createdBy").doesNotExist())
            .andExpect(jsonPath("$.modifiedBy").doesNotExist())
            .andExpect(jsonPath("$.field0").doesNotExist())
            .andExpect(jsonPath("$.pojo").exists())
            .andExpect(jsonPath("$.pojo.id").exists())
            .andExpect(jsonPath("$.pojo.createdBy").exists())
            .andExpect(jsonPath("$.pojo.modifiedBy").exists())
            .andExpect(jsonPath("$.pojo.field1").exists())
            .andExpect(jsonPath("$.pojo.field2").exists())
            .andExpect(jsonPath("$.pojo.field3").exists())
            .andReturn();
    log.info("{}", r.getResponse().getContentAsString());
  }
}
