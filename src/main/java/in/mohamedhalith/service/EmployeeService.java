package in.mohamedhalith.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.mohamedhalith.constant.Role;
import in.mohamedhalith.converter.EmployeeBeanConverter;
import in.mohamedhalith.csv.EmployeeBean;
import in.mohamedhalith.dao.EmployeeRepository;
import in.mohamedhalith.dto.AuthDTO;
import in.mohamedhalith.dto.LoginDTO;
import in.mohamedhalith.exception.ServiceException;
import in.mohamedhalith.exception.ValidationException;
import in.mohamedhalith.model.Employee;
import in.mohamedhalith.util.NumberValidator;
import in.mohamedhalith.util.PasswordUtil;
import in.mohamedhalith.util.StringValidator;
import in.mohamedhalith.util.UsernameUtil;
import in.mohamedhalith.validator.EmployeeValidator;
import in.mohamedhalith.validator.LoginValidator;

@Service
public class EmployeeService {

	// Instances of Classes needed in the service
	@Autowired
	EmployeeRepository employeeDAO;
	@Autowired
	EmployeeValidator employeeValidator;
	@Autowired
	LoginValidator loginValidator;
	@Autowired
	LeaveBalanceService leaveBalanceService;
	@Autowired
	EmployeeBeanConverter employeeBeanConverter;
	@Autowired
	UsernameUtil usernameUtil;
	@Autowired
	PasswordUtil passwordUtil;

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
	 * @throws ServiceException If database related exceptions occurs
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
	 * <p>
	 * Returns null if employee not found
	 * 
	 * @param employeeId Employee id provided by the organization
	 * @return Employee - Details of employee
	 * @throws ServiceException    When database related errors occurs (or) output
	 *                             is null(unable to fetch output)
	 * @throws ValidationException If null,empty or invalid type of input is
	 *                             provided
	 */
	public Employee getEmployee(int employeeId) throws ServiceException, ValidationException {
		Employee employee = null;
		NumberValidator.isValidEmployeeId(employeeId);
		employee = employeeDAO.findByEmployeeId(employeeId);
		// If employee is null, no data is found for given id
		if (employee == null) {
			// No employee is found with given id .
			// An exception is raised to indicate it
			throw new ServiceException("Invalid Employee Id");
		}
		return employee;
	}

	/**
	 * This method is used to get id of an employee when username is given as input
	 * 
	 * <p>
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
			// Exceptions faced during the query is captured and handled as ServiceException
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
	 * <p>
	 * Return boolean true for valid username and password and false for invalid
	 * details
	 * 
	 * @param username Username of the employee
	 * @param password Password of the employee
	 * @param role     Role of the employee
	 * @return boolean - True only if username,password and role are valid, false
	 *         otherwise
	 * @throws ServiceException    When database related errors occurs
	 * @throws ValidationException
	 */
	public AuthDTO findByUsernameAndPassword(LoginDTO user) throws ServiceException, ValidationException {
		loginValidator.verifyCredentials(user);
		try {
			Employee employee = employeeDAO.findByUsernameAndPasswordAndRole(user.getUsername(), user.getPassword(),
					user.getRole());
			AuthDTO loggedInUser;
			if (employee != null) {
				loggedInUser = new AuthDTO(employee.getName(), employee.getEmployeeId(), employee.getRole(),
						employee.getId());
			} else {
				throw new ValidationException("Invalid credentials");
			}
			return loggedInUser;
		} catch (Exception e) {
			// Exceptions faced during the query is captured and handled as ServiceException
			throw new ServiceException(e, "Unable to verify user");
		}
	}

	/**
	 * This method is used to add any new recruit or employee to the organization's
	 * records.
	 * 
	 * <p>
	 * Boolean value of true is returned if the employee is added successfully
	 * without any errors and false otherwise.
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
			// Exceptions faced during the query is captured and handled as ServiceException
			throw new ServiceException(e, errorMessage);
		}
		// If isAdded is false, performed operation is not expected operation
		// Hence Exception is thrown to indicate it
		if (!isAdded) {
			throw new ServiceException(errorMessage);
		}
		return isAdded;
	}

	/**
	 * This method is used to remove employee from the organization.
	 * 
	 * <p>
	 * Returns true if the employee is removed successfully from the records and
	 * exception is thrown with appropriate message.
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
			// Exceptions faced during the query is captured and handled as ServiceException
			throw new ServiceException(e, "Unable to remove employee");
		}
		boolean isRemoved = false;
		// If isAdded is false, performed operation is not expected operation
		// Hence Exception is thrown to indicate it
		if (rows != 1) {
			throw new ServiceException("Unable to remove employee");
		}
		isRemoved = leaveBalanceService.remove(employeeId);
		return isRemoved;
	}

	/**
	 * This method is used to check whether employee id is present in the records
	 * 
	 * <p>
	 * Returns true if it is already present and false otherwise
	 * 
	 * @param employeeId Employee id provided by the organization that is to be
	 *                   verified
	 * @return Integer - (Database)id of the employee if valid input is
	 *         provided,null otherwise
	 * @throws ServiceException When Database related exceptions occur
	 */
	public Integer exists(int employeeId) throws ServiceException {
		try {
			return employeeDAO.exists(employeeId);
		} catch (Exception e) {
			// Exceptions faced during the query is captured and handled as ServiceException
			throw new ServiceException("Unable to verify employee id");
		}
	}

	/**
	 * This method is used to check whether mobile number is present in the records
	 * 
	 * <p>
	 * Returns true if it is already present and false otherwise
	 * 
	 * @param mobileNumber Mobile number of the employee that is to be verified
	 * @return Integer - (Database)id of the employee if valid input is
	 *         provided,null otherwise
	 * @throws ServiceException When Database related exception occurs
	 */
	public Integer exists(long mobileNumber) throws ServiceException {
		try {
			return employeeDAO.exists(mobileNumber);
		} catch (Exception e) {
			// Exceptions faced during the query is captured and handled as ServiceException
			throw new ServiceException("Unable to verify mobile number");
		}
	}

	/**
	 * This method is used to check whether email id is present in the records.
	 * 
	 * <p>
	 * Returns true if it is already present and false otherwise
	 * 
	 * @param email Email id of the employee that is to be verified
	 * @return Integer - (Database)id of the employee if valid input is
	 *         provided,null otherwise
	 * @throws ServiceException When database related exception occurs
	 */
	public Integer exists(String email) throws ServiceException {
		try {
			return employeeDAO.exists(email);
		} catch (Exception e) {
			// Exceptions faced during the query is captured and handled as ServiceException
			throw new ServiceException("Unable to verify email id");
		}
	}

	public void addBulkEmployees(List<EmployeeBean> employeeBeanList) {
		List<Employee> employeeList = employeeBeanConverter.toEmployee(employeeBeanList);
		for (Employee employee : employeeList) {
			employee.setPassword(passwordUtil.generatePassword(employee.getName(), employee.getEmployeeId()));
			employee.setUsername(usernameUtil.generateUsername(employee.getName(), employee.getEmployeeId()));
			
		}

	}
}
