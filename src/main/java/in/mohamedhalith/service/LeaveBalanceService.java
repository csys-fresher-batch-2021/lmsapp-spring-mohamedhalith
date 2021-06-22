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
	 * Returns Employee for a valid employee id and null if it is invalid
	 * 
	 * @param employeeId Employee id provided by organization
	 * @return LeaveBalance - Remaining leave balance of the employee
	 * @throws ServiceException    When database related exceptions occurs
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
		// If output is null, no data is found
		if (leaveBalance == null) {
			throw new ServiceException("Invalid employee id");
		}
		return leaveBalance;
	}

	/**
	 * This method is used to remove the remaining balance of leave for an employee
	 * 
	 * @param employeeId Employee id provided by the organization
	 * @return boolean - True if leave balance is removed successfully
	 * @throws ServiceException    When database related exceptions occurs
	 * @throws ValidationException If null,empty or invalid employee id is provided
	 */
	public boolean remove(int employeeId) throws ServiceException, ValidationException {
		employeeValidator.isEmployee(employeeId);
		boolean isRemoved;
		try {
			isRemoved = leaveBalanceRepo.remove(employeeId);
		} catch (Exception e) {
			throw new ServiceException(e, "Unable to remove employee leave balance");
		}
		// If output is false, performed operation is not expected output
		if (!isRemoved) {
			throw new ServiceException("Unable to remove employee leave balance");
		}
		return isRemoved;
	}

	/**
	 * 
	 * @param employeeId Employee id provided by the organization
	 * @return boolean - True if leave balance of employee is successfully added
	 * @throws ServiceException When database related exceptions occur
	 */
	public boolean addLeaveBalance(int employeeId) throws ServiceException {
		boolean isAdded = leaveBalanceRepo.save(employeeId);
		if (!isAdded) {
			throw new ServiceException("Unable to add leave balance of the employee");
		}
		return isAdded;
	}

	/**
	 * 
	 * @param employeeId   Employee id provided by the organization
	 * @param leaveRequest LeaveRequest based on which the update operation is
	 *                     performed
	 * @return boolean - True if leave balance is updated successfully
	 * @throws ServiceException    When database related exceptions occurs
	 * @throws ValidationException If null,empty or invalid employee id is provided
	 */
	public boolean update(int employeeId, LeaveRequest leaveRequest) throws ValidationException, ServiceException {
		employeeValidator.isEmployee(employeeId);
		boolean isUpdated = leaveBalanceRepo.update(employeeId, leaveRequest);
		if (!isUpdated) {
			throw new ServiceException("Unable to update the leave balance of employee");
		}
		return isUpdated;
	}
}
