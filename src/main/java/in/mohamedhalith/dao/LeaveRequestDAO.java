package in.mohamedhalith.dao;

import java.util.List;

import in.mohamedhalith.model.LeaveRequest;

public interface LeaveRequestDAO {

	/**
	 * This method is used to find all the leave requests applied by the employees
	 * 
	 * @return List - List of all leave request
	 */
	List<LeaveRequest> findAll();

	/**
	 * This method is used to find leave requests applied by an employee with
	 * employee id as a reference.
	 * 
	 * @param employeeId
	 * @return LeaveRequest
	 */
	List<LeaveRequest> findByEmployeeId(int employeeId);

	/**
	 * This method is used to save leave request into the Database. Returns true if
	 * leave request is applied successfully without any errors and false otherwise
	 * 
	 * @param leaveRequest
	 * @return boolean
	 */
	boolean save(LeaveRequest leaveRequest);

	/**
	 * This method is used to find a leave request with the id(database id) of the
	 * request.
	 * 
	 * @param leaveId
	 * @return LeaveRequest
	 */
	LeaveRequest findById(int leaveId);

	/**
	 * This method is used to update the status of the leave request.A String
	 * variable, action is given as an input parameter along with the id of the
	 * leave request. The status of the leave request is updated according to the
	 * action specified in the string
	 * 
	 * @param action
	 * @param leaveId
	 * @return boolean
	 */
	boolean update(String action, int leaveId);

	/**
	 * This method is used to find whether a leave request is present for given
	 * dates or not. Input argument is a leave request containing dates for the
	 * leave and employee id of the employee who applied for leave
	 * 
	 * @param leaveRequest
	 * @return Integer
	 */
	Integer isExistingDate(LeaveRequest leaveRequest);

}