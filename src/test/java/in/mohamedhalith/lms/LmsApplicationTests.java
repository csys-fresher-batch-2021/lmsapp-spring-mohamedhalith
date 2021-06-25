package in.mohamedhalith.lms;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import in.mohamedhalith.dao.EmployeeRepository;
import in.mohamedhalith.exception.ValidationException;
import in.mohamedhalith.validator.EmployeeValidator;

@SpringBootTest
class LmsApplicationTests {

	@Autowired
	EmployeeRepository validator;

	@Test
	void contextLoads() {
		System.out.println(validator.findByUsernameAndPasswordAndRole("moha2627", "2627moha", "employee"));
		assertTrue(true);
	}

}
