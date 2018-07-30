package examples.serialize_all_except;

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
            .perform(get("/examples.serialize_all_except"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.createdBy").exists())
            .andExpect(jsonPath("$.modifiedBy").exists())
            .andExpect(jsonPath("$.field1").doesNotExist())
            .andExpect(jsonPath("$.field2").doesNotExist())
            .andExpect(jsonPath("$.field3").doesNotExist())
            .andReturn();
    log.info("{}", r.getResponse().getContentAsString());
  }
}
