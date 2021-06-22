package in.mohamedhalith.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.mohamedhalith.dao.LeaveRequestDAO;
import in.mohamedhalith.exception.ServiceException;
import in.mohamedhalith.exception.ValidationException;
import in.mohamedhalith.model.LeaveRequest;
import in.mohamedhalith.util.StringValidator;
import in.mohamedhalith.validator.EmployeeValidator;
import in.mohamedhalith.validator.LeaveRequestValidator;

@Service
public class LeaveRequestService {

	@Autowired
	LeaveRequestValidator leaveRequestValidator;
	@Autowired
	EmployeeValidator employeeValidator;
	@Autowired
	LeaveRequestDAO leaveRequestRepo;
	@Autowired
	LeaveBalanceService leaveBalanceService;

	/**
	 * Constructor is made private to prevent creating object of the class
	 */
	private LeaveRequestService() {
		// Default Constructor
	}

	/**
	 * This method is used to all get list of leave requests.
	 * 
	 * @return List - List of all leave requests
	 * @throws ServiceException When database related exception occurs
	 */
	public List<LeaveRequest> getRequestList() throws ServiceException {
		try {
			return leaveRequestRepo.findAll();
		} catch (Exception e) {
			throw new ServiceException(e, "Unable to get all requests");
		}
	}

	/**
	 * This method is used to get leave requests of a particular employee.
	 * 
	 * Returns the list of leave requests applied by an employee
	 * 
	 * @param employeeId Employee id provided by the organization
	 * @return List - List of all leave requests
	 * @throws ServiceException    When database related exception occurs
	 * @throws ValidationException If null,empty or invalid employee id
	 */
	public List<LeaveRequest> getEmployeeRequests(int employeeId) throws ValidationException, ServiceException {
		employeeValidator.isEmployee(employeeId);
		try {
			return leaveRequestRepo.findByEmployeeId(employeeId);
		} catch (Exception e) {
			throw new ServiceException(e, "Unable to get employee's requests");
		}
	}

	/**
	 * This method is used to apply for leave. Input parameters are leave request
	 * with all necessary details and employee id of the employee
	 * 
	 * Returns a message if applied successfully, else exception was thrown
	 * 
	 * @param leaveRequest LeaveRequest that is to be added to the records
	 * @param employeeId   Employee id provided by the organization
	 * @return boolean - True if leave request is applied successfully
	 * @throws ServiceException    When database related exception occurs
	 * @throws ValidationException If null,empty or invalid employee id
	 */
	public boolean applyLeaveRequest(LeaveRequest leaveRequest, int employeeId)
			throws ServiceException, ValidationException {
		// Default values
		leaveRequest.setAppliedTime(LocalDateTime.now());
		leaveRequest.setStatus("waiting for approval");

		leaveRequestValidator.isValidRequest(leaveRequest, employeeId);
		try {
			leaveBalanceService.update(employeeId, leaveRequest);
			return leaveRequestRepo.save(leaveRequest);
		} catch (Exception e) {
			throw new ServiceException(e, "Unable to apply for leave");
		}
	}

	/**
	 * This method is used to cancel leave request applied by an employee. Input
	 * parameters are id of leave request to be cancelled and employee id of the
	 * employee
	 * 
	 * Returns a boolean value, true if successfully cancelled.
	 * 
	 * @param leaveId    (Database)id of the leave request
	 * @param employeeId Employee id provided by the organization
	 * @return boolean - True if the leave request is cancelled successfully
	 * @throws ServiceException    When database related exceptions occurs
	 * @throws ValidationException If null,empty or invalid leaveId or employeeId is
	 *                             provided
	 */
	public boolean cancelLeaveRequest(int leaveId, int employeeId) throws ServiceException, ValidationException {
		leaveRequestValidator.isValidId(leaveId);
		employeeValidator.isEmployee(employeeId);
		try {
			return leaveRequestRepo.update("cancel", leaveId);
		} catch (Exception e) {
			throw new ServiceException(e, "Unable to cancel leave request");
		}
	}

	/**
	 * This method is used to update the leave request applied by the employee.Input
	 * parameters are action to be performed and id of the leave request
	 * 
	 * @param action  String defining how the request is to be updated
	 * @param leaveId Id of the leave request
	 * @return boolean - True if the leave request is updated successfully
	 * @throws ServiceException    When database related exceptions occurs
	 * @throws ValidationException If null,empty or invalid leaveId or action is
	 *                             provided
	 */
	public boolean updateLeaveRequest(String action, int leaveId) throws ValidationException, ServiceException {
		leaveRequestValidator.isValidId(leaveId);
		StringValidator.isValidAction(action);
		try {
			return leaveRequestRepo.update(action, leaveId);
		} catch (Exception e) {
			throw new ServiceException(e, "Unable to update the leave request");
		}
	}

	/**
	 * This method is used to get a leave request with its id
	 * 
	 * @param leaveId Id of the leave request
	 * @return LeaveRequest Leave request if valid id is provided,null else
	 * @throws ServiceException When database related exceptions occurs
	 */
	public LeaveRequest getLeaveRequest(int leaveId) throws ServiceException {
		LeaveRequest leaveRequest = null;
		try {
			leaveRequest = leaveRequestRepo.findById(leaveId);
		} catch (Exception e) {
			throw new ServiceException(e, "Unable to find leave request");
		}
		// If output is false, performed operation is not expected output
		if (leaveRequest == null) {
			throw new ServiceException("Invalid Leave request Id");
		}
		return leaveRequest;
	}
}
