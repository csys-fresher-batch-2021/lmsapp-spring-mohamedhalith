package in.mohamedhalith.validator;

import java.util.ArrayList;
import java.util.List;

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
	 * @param username username of the user
	 * @throws ValidationException If the input is null,empty or in invalid format
	 */
	public void isEmployee(String username) throws ValidationException {
		try {
			employeeService.getEmployeeId(username);
		} catch (ServiceException e) {
			throw new ValidationException(INVALIDEMPLOYEE);
		}
	}

	/**
	 * This method is used to validate or verify given id of the employee
	 * 
	 * <p>
	 * Returns true if an employee is present with the given id
	 * 
	 * @param employeeId Id of the employee provided by the organization
	 * @return boolean - True if an employee is present with the given id
	 * @throws ValidationException When given input is null,empty or in invalid
	 *                             format (or) no employee is found for given id
	 */
	public boolean isEmployee(int employeeId) throws ValidationException {
		try {
			employeeService.getEmployee(employeeId);
			return true;
		} catch (ServiceException e) {
			// ServiceException is handled as ValidationException since no employee is
			// present for given id
			throw new ValidationException(e, INVALIDEMPLOYEE);
		}
	}

	/**
	 * This method is used to validates the fields of the employee and verifies
	 * whether each of them is valid
	 * 
	 * @param employeeId Id of the employee given by the organization
	 * @throws ValidationException If any input is found to be null,empty or in
	 *                             invalid format
	 * @throws ServiceException    When database related exceptions occur
	 */
	public void isValidEmployee(Employee employee) throws ValidationException, ServiceException {
		NumberValidator.isValidEmployeeId(employee.getEmployeeId());
		NumberValidator.isValidMobileNumber(employee.getMobileNumber());
		StringValidator.isValidName(employee.getName());
		StringValidator.isValidEmail(employee.getEmail());
		StringValidator.isValidPassword(employee.getPassword());
		StringValidator.isValidUsername(employee.getUsername());
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
	
	public List<String> isValidEmployees(List<Employee> employeeList)  {
		List<String> errorMessageList = new ArrayList<>();
		for (Employee employee : employeeList) {
			try {
				NumberValidator.isValidEmployeeId(employee.getEmployeeId());
				NumberValidator.isValidMobileNumber(employee.getMobileNumber());
				StringValidator.isValidName(employee.getName());
				StringValidator.isValidEmail(employee.getEmail());
				StringValidator.isValidPassword(employee.getPassword());
				StringValidator.isValidUsername(employee.getUsername());
			} catch (ValidationException e) {
				StringBuilder errorMessage = new StringBuilder();
				errorMessage.append(e.getMessage()).append("-EmployeeId: ").append(employee.getEmployeeId());;
				errorMessage.append(" Name: ").append(employee.getName());
				errorMessageList.add(errorMessage.toString());
			}
		}
		return errorMessageList;
	}
}
