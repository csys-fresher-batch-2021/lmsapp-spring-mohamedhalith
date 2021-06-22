package in.mohamedhalith.lms;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import in.mohamedhalith.dao.LeaveRequestDAO;

@SpringBootTest
class LmsApplicationTests {

	@Autowired
	LeaveRequestDAO validator;

	@Test
	void contextLoads() {
		System.out.println(validator.findAll());
		assertTrue(true);
	}

}
