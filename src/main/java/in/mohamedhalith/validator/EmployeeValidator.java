package in.mohamedhalith.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.mohamedhalith.exception.ServiceException;
import in.mohamedhalith.exception.ValidationException;
import in.mohamedhalith.model.Employee;
import in.mohamedhalith.service.EmployeeService;
import in.mohamedhalith.util.DateValidator;
import in.mohamedhalith.util.NumberValidator;
import in.mohamedhalith.util.StringValidator;

@Component
public class EmployeeValidator {
	private EmployeeValidator() {
		// Default Constructor
	}

	@Autowired
	EmployeeService employeeService;
	private static final String INVALIDEMPLOYEE = "Invalid Employee details";

	/**
	 * This method is used to verify whether the user is an employee or not
	 * 
	 * @param username
	 * @throws ValidationException
	 * @throws ServiceException
	 */
	public void isEmployee(String username) throws ValidationException, ServiceException {
		try {
			employeeService.getEmployeeId(username);
		} catch (ServiceException e) {
			throw new ValidationException(INVALIDEMPLOYEE);
		}
	}

	/**
	 * This method is used to validate or verify given employee id
	 * 
	 * @param employeeId
	 * @return boolean
	 * @throws ValidationException
	 */
	public boolean isEmployee(int employeeId) throws ValidationException {
		Employee employee = null;
		try {
			employee = employeeService.getEmployee(employeeId);
			boolean isValid = false;
			if (employee != null) {
				isValid = true;
			}
			return isValid;
		} catch (ServiceException e) {
			throw new ValidationException(e,INVALIDEMPLOYEE);
		}
	}

	/**
	 * This method is used to validates the fields of the employee and verifies
	 * whether each of them is valid
	 * 
	 * @param employeeId
	 * @throws ValidationException
	 */
	public void isValidEmployee(Employee employee) throws ValidationException {
		StringValidator.isValidName(employee.getName());
		StringValidator.isValidEmail(employee.getEmail());
		StringValidator.isValidPassword(employee.getPassword());
		StringValidator.isValidUsername(employee.getUsername());
		NumberValidator.isValidMobileNumber(employee.getMobileNumber());
			Integer id = employeeService.exists(employee.getEmployeeId());
			if (id != null) {
				throw new ValidationException("Employee Id already exists");
			}
			id = employeeService.exists(employee.getMobileNumber());
			if (id != null) {
				throw new ValidationException("Mobile Number already exists");
			}
			id = employeeService.exists(employee.getEmail());
			if (id != null) {
				throw new ValidationException("Email Id already exists");
			}
		DateValidator.isValidJoinedDate(employee.getJoinedDate());
	}
}
