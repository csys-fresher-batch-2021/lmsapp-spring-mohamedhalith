package in.mohamedhalith.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import in.mohamedhalith.constant.RequestStatus;
import in.mohamedhalith.constant.UpdateAction;
import in.mohamedhalith.dao.LeaveRequestDAO;
import in.mohamedhalith.model.Employee;
import in.mohamedhalith.model.LeaveRequest;

@Repository
public class LeaveRequestImpl implements LeaveRequestDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * This method is used to find all the leave requests applied by the employees
	 * 
	 * @return List - List of all leave request
	 */
	@Override
	public List<LeaveRequest> findAll() {
		String sql = "SELECT lr.id,e.name,e.employee_id,lr.from_date,lr.to_date,lr.leave_type,lr.status,"
				+ "lr.reason,lr.created_time,lr.duration"
				+ " FROM leave_requests lr, employees e WHERE e.employee_id = lr.employee_id ORDER BY from_date ASC";
		return jdbcTemplate.query(sql, (result, rowNo) -> {
			LeaveRequest leaveRequest = new LeaveRequest();
			return returnAsLeaveRequest(result, leaveRequest);
		});
	}

	/**
	 * This method is used to find leave requests applied by an employee. Id of the
	 * employee is given as the input parameter
	 * 
	 * @param employeeId Employee id provided by the organization
	 * @return List - List of leave requests applied by an employee
	 */
	@Override
	public List<LeaveRequest> findByEmployeeId(int employeeId) {
		String sql = "SELECT lr.id,e.name,e.employee_id,lr.from_date,lr.to_date,lr.leave_type,lr.status,"
				+ "lr.reason,lr.created_time,lr.duration"
				+ " FROM leave_requests lr, employees e WHERE e.employee_id = lr.employee_id AND lr.employee_id = ?"
				+ " ORDER BY from_date ASC";
		return jdbcTemplate.query(sql, (result, rowNo) -> {
			LeaveRequest leaveRequest = new LeaveRequest();
			return returnAsLeaveRequest(result, leaveRequest);
		}, employeeId);
	}

	/**
	 * This method is used to save leave request into the Database. Returns true if
	 * leave request is applied successfully without any errors and false otherwise
	 * 
	 * @param leaveRequest LeaveRequest applied by the employee
	 * @return boolean - True if leave request is saved successfully,false else
	 */
	@Override
	public boolean save(LeaveRequest leaveRequest) {
		String sql = "INSERT INTO leave_requests (employee_id,from_date,to_date,leave_type,duration,reason,created_time)"
				+ "VALUES(?,?,?,?,?,?,?)";
		Object[] params = { leaveRequest.getEmployee().getEmployeeId(), leaveRequest.getFromDate(),
				leaveRequest.getToDate(), leaveRequest.getType().toLowerCase(), leaveRequest.getDuration(),
				leaveRequest.getReason(), leaveRequest.getAppliedTime() };
		int row = jdbcTemplate.update(sql, params);
		boolean isAdded = false;
		if (row == 1) {
			isAdded = true;
		}
		return isAdded;
	}

	/**
	 * This method is used to find a leave request with the id(database id) of the
	 * request.
	 * 
	 * @param leaveId Id of the leave request
	 * @return LeaveRequest LeaveRequest with the given id(if id is valid) and null
	 *         otherwise
	 */
	@Override
	public LeaveRequest findById(int leaveId) {
		String sql = "SELECT employee_id,from_date,to_date,leave_type,duration,status,created_time,reason "
				+ "FROM leave_requests WHERE id = ?";
		return jdbcTemplate.queryForObject(sql, (result, rowNo) -> {
			LeaveRequest leaveRequest = new LeaveRequest();
			leaveRequest.setLeaveId(leaveId);
			leaveRequest.setFromDate(result.getDate("from_date").toLocalDate());
			leaveRequest.setToDate(result.getDate("to_date").toLocalDate());
			leaveRequest.setType(result.getString("leave_type"));
			leaveRequest.setStatus(result.getString("status"));
			leaveRequest.setDuration(result.getInt("duration"));
			leaveRequest.setReason(result.getString("reason"));
			leaveRequest.setAppliedTime(result.getTimestamp("created_time").toLocalDateTime());
			Employee employee = new Employee();
			employee.setEmployeeId(result.getInt("employee_id"));
			leaveRequest.setEmployee(employee);
			return leaveRequest;
		}, leaveId);
	}

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
	@Override
	public boolean update(String action, int leaveId) {
		String status = null;
		if (action.equalsIgnoreCase(UpdateAction.CANCEL.toString())) {
			status = RequestStatus.CANCELLED.toString();
		} else if (action.equalsIgnoreCase(UpdateAction.APPROVE.toString())) {
			status = RequestStatus.APPROVED.toString();
		} else if (action.equalsIgnoreCase(UpdateAction.REJECT.toString())) {
			status = RequestStatus.REJECTED.toString();
		}
		String sql = "UPDATE leave_requests SET status = LOWER(?),modified_time = now() WHERE id = ?";
		Object[] params = { status, leaveId };
		int row = jdbcTemplate.update(sql, params);
		boolean isUpdated = false;
		if (row == 1) {
			isUpdated = true;
		}
		return isUpdated;
	}

	/**
	 * This method is used to find whether a leave request is present for given
	 * dates or not. Input argument is a leave request containing dates for the
	 * leave and employee id of the employee who applied for leave
	 * 
	 * @param leaveRequest
	 * @return Integer
	 */
	@Override
	public Integer isExistingDate(LeaveRequest leaveRequest) {
		String sql = "SELECT id FROM leave_requests WHERE ((? between from_date AND to_date) OR  (? between from_date AND to_date))"
				+ "AND (status = 'waiting for approval' OR status = 'approved') AND employee_id = ?";
		Object[] params = { leaveRequest.getFromDate(), leaveRequest.getToDate(),
				leaveRequest.getEmployee().getEmployeeId() };
		Integer id = null;
		try {
			id = jdbcTemplate.queryForObject(sql, Integer.class, params);
		} catch (DataAccessException e) {
			id = 0;
		}
		return id;
	}

	/**
	 * This method is used to convert the result set obtained from the query into an
	 * object(instance) of leave request
	 * 
	 * @param result       ResultSet passed as argument
	 * @param leaveRequest LeaveRequest instance to be stored
	 * @return LeaveRequest LeaveRequest with appropriate details
	 * @throws SQLException When database related exception occurs
	 */
	private LeaveRequest returnAsLeaveRequest(ResultSet result, LeaveRequest leaveRequest) throws SQLException {
		leaveRequest.setLeaveId(result.getInt("id"));
		leaveRequest.setFromDate(result.getDate("from_date").toLocalDate());
		leaveRequest.setToDate(result.getDate("to_date").toLocalDate());
		leaveRequest.setType(result.getString("leave_type"));
		leaveRequest.setStatus(result.getString("status"));
		leaveRequest.setReason(result.getString("reason"));
		leaveRequest.setDuration(result.getInt("duration"));
		leaveRequest.setAppliedTime(result.getTimestamp("created_time").toLocalDateTime());
		Employee employee = new Employee();
		employee.setName(result.getString("name"));
		employee.setEmployeeId(result.getInt("employee_id"));
		leaveRequest.setEmployee(employee);
		return leaveRequest;
	}

}
