package in.mohamedhalith.lms;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import in.mohamedhalith.exception.ValidationException;
import in.mohamedhalith.validator.EmployeeValidator;

@SpringBootTest
class LmsApplicationTests {

	@Autowired
	EmployeeValidator validator;

	@Test
	void contextLoads() {
		try {
			System.out.println(validator.isEmployee(2382));
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(true);
	}

}
