package webAppBook;


import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
@TestPropertySource("classpath:application-test.properties")
public class IntegrationTest {
	
	@Autowired
	protected WebApplicationContext wac;
	
	protected MockMvc mockMvc;
	
//	@Before
//	public void initMockMcv {
//		mockMvc = MockMvcBuilder.webAppContextSetup(wac).build();
//	}
}
