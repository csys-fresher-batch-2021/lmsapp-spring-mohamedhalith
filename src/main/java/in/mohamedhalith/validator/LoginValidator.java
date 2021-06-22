package in.mohamedhalith.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.mohamedhalith.constant.Role;
import in.mohamedhalith.dto.LoginDTO;
import in.mohamedhalith.exception.ServiceException;
import in.mohamedhalith.exception.ValidationException;
import in.mohamedhalith.service.EmployeeService;
import in.mohamedhalith.util.StringValidator;

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
	 * @param username
	 * @param password
	 * @param role
	 * @return 
	 * @return boolean
	 * @throws ValidationException
	 * @throws ServiceException 
	 */
	public boolean verifyCredentials(LoginDTO user) throws ValidationException, ServiceException {
		String username = user.getUsername();
		String password = user.getPassword();
		String role = user.getRole();
		boolean valid = false;
		
		StringValidator.isValidUsername(username);
		StringValidator.isValidPassword(password);
		StringValidator.isValidString(role);
		if (role.equalsIgnoreCase(Role.EMPLOYEE.toString()) || role.equalsIgnoreCase(Role.MANAGER.toString())) {
			// Verifies user name and password with employee list
			valid = employeeService.findByUsernameAndPassword(username, password,role);
		}
		return valid;
	}
}
