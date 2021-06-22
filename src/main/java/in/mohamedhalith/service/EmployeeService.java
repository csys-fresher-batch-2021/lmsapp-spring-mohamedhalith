package in.mohamedhalith.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.mohamedhalith.constant.Role;
import in.mohamedhalith.dao.EmployeeRepository;
import in.mohamedhalith.exception.ServiceException;
import in.mohamedhalith.exception.ValidationException;
import in.mohamedhalith.model.Employee;
import in.mohamedhalith.util.NumberValidator;
import in.mohamedhalith.util.StringValidator;
import in.mohamedhalith.validator.EmployeeValidator;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeDAO;
	@Autowired
	EmployeeValidator employeeValidator;
	@Autowired
	LeaveBalanceService leaveBalanceService;

	/**
	 * Constructor is made private to prevent creating object of the class
	 */
	private EmployeeService() {
		// Default constructor
	}

	/**
	 * This method is used to return the list of employees
	 * 
	 * @return - List of employees
	 * @throws ServiceException If database related exceptions
	 */
	public Iterable<Employee> getEmployeeList() throws ServiceException {
		try {
			return employeeDAO.findAll();
		} catch (Exception e) {
			throw new ServiceException(e, "Unable to get Employee list");
		}
	}

	/**
	 * This method is used to get employee from the DAO.
	 * 
	 * Returns null if employee not found
	 * 
	 * @param employeeId Employee id provided by the organization
	 * @return Employee - Details of employee
	 * @throws ServiceException    When database related errors occurs
	 * @throws ValidationException If null,empty or invalid type of input is
	 *                             provided
	 */
	public Employee getEmployee(int employeeId) throws ServiceException, ValidationException {
		Employee employee = null;
		NumberValidator.isValidEmployeeId(employeeId);
		employee = employeeDAO.findByEmployeeId(employeeId);
		// If employee is null, no data is found for given id
		if (employee == null) {
			throw new ServiceException("Invalid Employee Id");
		}
		return employee;
	}

	/**
	 * This method is used to get id of an employee when username is given as input
	 * 
	 * Returns an integer if valid username is given and exception is thrown if it
	 * is null
	 * 
	 * @param username Username of the employee
	 * @return Integer - Employee id
	 * @throws ServiceException    When database related errors occurs
	 * @throws ValidationException If null,empty or invalid type of input is
	 *                             provided
	 */
	public Integer getEmployeeId(String username) throws ServiceException, ValidationException {
		StringValidator.isValidUsername(username);
		Integer employeeId;
		try {
			employeeId = employeeDAO.findEmployeeId(username);
		} catch (Exception e) {
			throw new ServiceException(e, "Unable to get employee");
		}
		// If employee is null, no data is found for given username
		if (employeeId == null) {
			throw new ServiceException("Invalid Employee username");
		}
		return employeeId;
	}

	/**
	 * This method is used to verify an employee by username and password
	 * 
	 * Return boolean true for valid username and password and false for invalid
	 * details
	 * 
	 * @param username Username of the employee
	 * @param password Password of the employee
	 * @param role     Role of the employee
	 * @return boolean - True only if username,password and role are valid, false
	 *         otherwise
	 * @throws ServiceException When database related errors occurs
	 */
	public boolean findByUsernameAndPassword(String username, String password, String role) throws ServiceException {
		boolean isValid = false;
		Employee employee;
		try {
			employee = employeeDAO.findByUsernameAndPasswordAndRole(username, password, role);
		} catch (Exception e) {
			throw new ServiceException(e, "Unable to verify user");
		}
		if (employee != null) {
			isValid = true;
		}
		return isValid;
	}

	/**
	 * This method is used to add any new recruit or employee to the organization's
	 * records. Boolean value of true is returned if the employee is added
	 * successfully without any errors and false otherwise.
	 * 
	 * @param employee Details of employee who is to be added
	 * @return boolean - True only if all the details of the employee are valid and
	 *         correct
	 * @throws ServiceException    When database related errors occurs
	 * @throws ValidationException If null,empty or invalid type of input is
	 *                             provided
	 */
	public boolean addEmployee(Employee employee) throws ServiceException, ValidationException {
		employee.setModifiedTime(LocalDateTime.now());
		String role = Role.EMPLOYEE.toString().toLowerCase();
		employee.setRole(role);
		employeeValidator.isValidEmployee(employee);
		String errorMessage = "Unable to add employee";
		// If isAdded is false, performed operation is not expected operation
		boolean isAdded;
		try {
			employeeDAO.save(employee);
			isAdded = leaveBalanceService.addLeaveBalance(employee.getEmployeeId());
		} catch (Exception e) {
			throw new ServiceException(e, "Unable to add employee");
		}
		// If isAdded is false, performed operation is not expected operation
		if (!isAdded) {
			throw new ServiceException(errorMessage);
		}
		return isAdded;
	}

	/**
	 * This method is used to remove employee from the organization. Returns true if
	 * the employee is removed successfully from the records and exception is thrown
	 * with appropriate message.
	 * 
	 * @param employeeId Employee id provided by the organization
	 * @return boolean - True only if the employee is removed successfully
	 * @throws ServiceException    When database related errors occurs
	 * @throws ValidationException if null,empty or invalid type of input is
	 *                             provided
	 */
	public boolean removeEmployee(int employeeId) throws ValidationException, ServiceException {
		employeeValidator.isEmployee(employeeId);
		int rows;
		try {
			rows = employeeDAO.remove(employeeId);
		} catch (Exception e) {
			throw new ServiceException(e, "Unable to remove employee");
		}
		boolean isRemoved = false;
		// If isAdded is false, performed operation is not expected operation
		if (rows != 1) {
			throw new ServiceException("Unable to remove employee");
		}
		isRemoved = leaveBalanceService.remove(employeeId);
		return isRemoved;
	}

	/**
	 * This method is used to check whether employee id is present in the records
	 * 
	 * @param employeeId Employee id provided by the organization that is to be
	 *                   verified
	 * @return Integer - (Database)id of the employee if valid input is
	 *         provided,null otherwise
	 */
	public Integer exists(int employeeId) {
		return employeeDAO.exists(employeeId);
	}

	/**
	 * This method is used to check whether mobile number is present in the records
	 * 
	 * @param mobileNumber Mobile number of the employee that is to be verified
	 * @return Integer - (Database)id of the employee if valid input is
	 *         provided,null otherwise
	 */
	public Integer exists(long mobileNumber) {
		return employeeDAO.exists(mobileNumber);
	}

	/**
	 * This method is used to check whether email id is present in the records
	 * 
	 * @param email Email id of the employee that is to be verified
	 * @return Integer - (Database)id of the employee if valid input is
	 *         provided,null otherwise
	 */
	public Integer exists(String email) {
		return employeeDAO.exists(email);
	}
}
