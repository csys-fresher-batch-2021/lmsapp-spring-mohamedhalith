package in.mohamedhalith.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.mohamedhalith.exception.ServiceException;
import in.mohamedhalith.exception.ValidationException;
import in.mohamedhalith.dao.LeaveBalanceDAO;
import in.mohamedhalith.model.LeaveBalance;
import in.mohamedhalith.model.LeaveRequest;
import in.mohamedhalith.validator.EmployeeValidator;

@Service
public class LeaveBalanceService {

	// Instances of Classes required in this service class
	@Autowired
	EmployeeValidator employeeValidator;
	@Autowired
	LeaveBalanceDAO leaveBalanceRepo;

	/**
	 * Constructor is made private to prevent creating object of the class
	 */
	private LeaveBalanceService() {
		// Default Constructor
	}

	/**
	 * This method is used to find the leave balance of an employee. Employee id is
	 * given as input.
	 * 
	 * <p>
	 * Returns Employee for a valid employee id and null if it is invalid
	 * 
	 * @param employeeId Employee id provided by organization
	 * @return LeaveBalance - Remaining leave balance of the employee
	 * @throws ServiceException    When database related exceptions occurs (or)
	 *                             output is null(unable to fetch output)
	 * @throws ValidationException If null,empty or invalid employee id is provided
	 */
	public LeaveBalance findLeaveBalance(int employeeId) throws ServiceException, ValidationException {
		employeeValidator.isEmployee(employeeId);
		LeaveBalance leaveBalance = null;
		try {
			leaveBalance = leaveBalanceRepo.findById(employeeId);
		} catch (Exception e) {
			throw new ServiceException(e, "Invalid employee id");
		}
		// If output is null, no data is found and exception is raised
		if (leaveBalance == null) {
			throw new ServiceException("Invalid employee id");
		}
		return leaveBalance;
	}

	/**
	 * This method is used to remove the remaining balance of leave for an employee
	 * <p>
	 * Returns true if leave balance is removed successfully, false else
	 * 
	 * @param employeeId Employee id provided by the organization
	 * @return boolean - True if leave balance is removed successfully
	 * @throws ServiceException    When database related exceptions occurs (or)
	 *                             operation is not completed successfully
	 * @throws ValidationException If null,empty or invalid employee id is provided
	 */
	public boolean remove(int employeeId) throws ServiceException, ValidationException {
		boolean isRemoved;
		try {
			isRemoved = leaveBalanceRepo.remove(employeeId);
		} catch (Exception e) {
			// Exceptions faced during the query is captured and handled as ServiceException
			throw new ServiceException(e, "Unable to remove employee leave balance");
		}
		// If output is false, performed operation is not expected output
		// Exception is raised to indicate it
		if (!isRemoved) {
			throw new ServiceException("Unable to remove employee leave balance");
		}
		return isRemoved;
	}

	/**
	 * This method is used to add records for an employee so that his/her leave
	 * balance can be maintained
	 * 
	 * <p>
	 * Returns true if leave balance of employee is successfully added,false else
	 * 
	 * @param employeeId Employee id provided by the organization
	 * @return boolean - True if leave balance of employee is successfully added
	 * @throws ServiceException When database related exceptions occur (or)
	 *                          operation is not completed successfully
	 */
	public boolean addLeaveBalance(int employeeId) throws ServiceException {
		boolean isAdded = leaveBalanceRepo.save(employeeId);
		// Database operation is completed successfully but it is not the expected
		// output
		// Hence ServiceException is thrown to indicate it
		if (!isAdded) {
			// Database operation is completed successfully but it is not the expected
			// output
			// Hence ServiceException is thrown to indicate it
			throw new ServiceException("Unable to add leave balance of the employee");
		}
		return isAdded;
	}

	/**
	 * This method is used to update the remaining leaves for an employee whenever
	 * leave request is applied/cancelled/rejected
	 * <p>
	 * Returns true if leave balance is updated successfully
	 * 
	 * @param employeeId   Employee id provided by the organization
	 * @param leaveRequest LeaveRequest based on which the update operation is
	 *                     performed
	 * @return boolean - True if leave balance is updated successfully
	 * @throws ServiceException    When database related exceptions occurs (or)
	 *                             operation is not completed successfully
	 * @throws ValidationException If null,empty or invalid employee id is provided
	 */
	public boolean update(int employeeId, LeaveRequest leaveRequest) throws ValidationException, ServiceException {
		employeeValidator.isEmployee(employeeId);
		boolean isUpdated = leaveBalanceRepo.update(employeeId, leaveRequest);
		// Verifies returned output is true or not, if it is false it is not updated
		// properly and exception is raised
		if (!isUpdated) {
			throw new ServiceException("Unable to update the leave balance of employee");
		}
		return isUpdated;
	}
}
