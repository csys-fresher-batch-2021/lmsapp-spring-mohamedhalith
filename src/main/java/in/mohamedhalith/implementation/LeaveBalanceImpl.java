package in.mohamedhalith.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import in.mohamedhalith.dao.LeaveBalanceDAO;
import in.mohamedhalith.model.Employee;
import in.mohamedhalith.model.LeaveBalance;
import in.mohamedhalith.model.LeaveRequest;

@Repository
public class LeaveBalanceImpl implements LeaveBalanceDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;
	String leaveBalance = "leave_balance";

	/**
	 * This method is used to find the remaining leave balance of an employee.
	 * Employee id is provided as input and it used as the reference to find the
	 * remaining leave balance of the employee
	 * 
	 * @param employeeId Employee id provided by organization
	 * @return LeaveBalance - Remaining leave balance of the employee
	 */
	@Override
	public LeaveBalance findById(int employeeId) {
		String sql = "SELECT * FROM leavebalance_vw WHERE employee_id = ?";
		Object[] params = { employeeId };
		return jdbcTemplate.queryForObject(sql, (result, rowNo) -> {
			LeaveBalance leavebalance = new LeaveBalance();
			Employee employee = new Employee();
			employee.setName(result.getString("name"));
			employee.setEmployeeId(employeeId);
			leavebalance.setEmployee(employee);
			leavebalance.setSickLeave(result.getInt("sickleave"));
			leavebalance.setCasualLeave(result.getInt("casualleave"));
			leavebalance.setEarnedLeave(result.getInt("earnedleave"));
			return leavebalance;
		}, params);
	}

	/**
	 * This method is used to remove the employee from the records of the
	 * organization. Id of the employee is given as the input reference
	 * 
	 * @param employeeId employee id provided by organization
	 * @return boolean - True if table is altered and no. of modified rows are equal
	 *         to expected value
	 */
	@Override
	public boolean remove(int employeeId) {
		String sql = "UPDATE employee_leavebalance SET active = false WHERE employee_id = ? ";
		int row = jdbcTemplate.update(sql, employeeId);
		boolean isRemoved = false;
		if (row == 3) {
			isRemoved = true;
		}
		return isRemoved;
	}

	/**
	 * This method is used to insert leave balance for an employee where the id of
	 * the employee is provided and the leave balance is added to the employee with
	 * that id.
	 * 
	 * @param employeeId Employee id provided by organization
	 * @return boolean - True if table is altered and no. of modified rows are equal
	 *         to expected value
	 */
	@Override
	public boolean save(int employeeId) {
		String sql = "INSERT INTO employee_leavebalance(employee_id,type_of_leave,leave_balance,modified_time)"
				+ "VALUES(?,\'sickleave\',0,now())," + "(?,\'casualleave\',0,now())," + "(?,\'earnedleave\',0,now())";
		Object[] params = { employeeId, employeeId, employeeId };
		int row = jdbcTemplate.update(sql, params);
		boolean isAdded = false;
		if (row == 3) {
			isAdded = true;
		}
		return isAdded;
	}

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
	@Override
	public boolean update(int employeeId, LeaveRequest leaveRequest) {
		String leaveType = leaveRequest.getType().toLowerCase();
		String sql = "UPDATE employee_leavebalance SET leave_balance = leave_balance - ? WHERE employee_id = ? AND type_of_leave = ?";
		Object[] params = { leaveRequest.getDuration(), employeeId, leaveType };
		int row = jdbcTemplate.update(sql, params);
		boolean isUpdated = false;
		if (row == 1) {
			isUpdated = true;
		}
		return isUpdated;
	}

}
