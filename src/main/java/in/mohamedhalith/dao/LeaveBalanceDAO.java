package in.mohamedhalith.dao;

import in.mohamedhalith.model.LeaveBalance;
import in.mohamedhalith.model.LeaveRequest;

public interface LeaveBalanceDAO {

	/**
	 * This method is used to find the remaining leave balance of an employee.
	 * Employee id is provided as input and it used as the reference to find the
	 * remaining leave balance of the employee
	 * 
	 * @param employeeId Employee id provided by organization
	 * @return LeaveBalance - Remaining leave balance of the employee
	 */
	LeaveBalance findById(int employeeId);

	/**
	 * This method is used to remove the employee from the records of the
	 * organization. Id of the employee is given as the input reference
	 * 
	 * @param employeeId employee id provided by organization
	 * @return boolean - True if table is altered and no. of modified rows are equal
	 *         to expected value
	 */
	boolean remove(int employeeId);

	/**
	 * This method is used to insert leave balance for an employee where the id of
	 * the employee is provided and the leave balance is added to the employee with
	 * that id.
	 * 
	 * @param employeeId Employee id provided by organization
	 * @return boolean - True if table is altered and no. of modified rows are equal
	 *         to expected value
	 */
	boolean save(int employeeId);

	/**
	 * This method is used to update the leave balance of an employee where employee
	 * id is given as the input.
	 * 
	 * Return true if successfully updated the leave balance.
	 * 
	 * @param employeeId   employee id provided by organization
	 * @param leaveRequest LeaveRequest which is to be used as a reference
	 * @return boolean - True if table is altered and no. of modified rows are equal
	 *         to expected value
	 */
	boolean update(int employeeId, LeaveRequest leaveRequest);

}