package mathpar.web.learning.school;

import mathpar.web.learning.school._configs.LearningApplication;
import mathpar.web.learning.school.services.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = LearningApplication.class)
class LearningApplicationTests {
	//This bean is mocked because it's basically an external dependency gateway so it will never be used in tests
	@MockBean
	public AccountService accountService;

	@Test
	void contextLoads() {
	}

}
