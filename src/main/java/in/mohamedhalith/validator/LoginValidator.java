package in.mohamedhalith.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.mohamedhalith.constant.Role;
import in.mohamedhalith.dto.LoginDTO;
import in.mohamedhalith.exception.ValidationException;
import in.mohamedhalith.service.EmployeeService;

@Component
public class LoginValidator {

	private LoginValidator() {
		// Default Constructor
	}

	@Autowired
	EmployeeService employeeService;

	/**
	 * This method is used to verify whether the user's credentials are valid and
	 * also user is the admin of the system.
	 * 
	 * Returns True if the credentials are correct.
	 * 
	 * @param user user credentials
	 * @throws ValidationException When user credentials is invalid
	 */
	public void verifyCredentials(LoginDTO user) throws ValidationException {
		String role = user.getRole();

		if (!role.equalsIgnoreCase(Role.EMPLOYEE.toString()) && !role.equalsIgnoreCase(Role.MANAGER.toString())) {
			throw new ValidationException("Invalid credentials");
		}
	}
}
