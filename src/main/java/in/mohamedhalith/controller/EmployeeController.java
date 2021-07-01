package in.mohamedhalith.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.mohamedhalith.constant.FieldConstants;
import in.mohamedhalith.exception.ServiceException;
import in.mohamedhalith.exception.ValidationException;
import in.mohamedhalith.model.Employee;
import in.mohamedhalith.model.LeaveBalance;
import in.mohamedhalith.service.EmployeeService;
import in.mohamedhalith.service.LeaveBalanceService;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	@Autowired
	LeaveBalanceService leaveBalanceService;

	private static final String EMPLOYEE_ID = FieldConstants.EMPLOYEE_ID;

	@GetMapping("ListEmployeeServlet")
	public Iterable<Employee> listEmployee() throws ServiceException {
		return employeeService.getEmployeeList();
	}

	@PostMapping("AddEmployeeServlet")
	public boolean addEmployee(@RequestBody Employee employee)
			throws ServiceException, ValidationException {
//		Message message = new Message();
		return employeeService.addEmployee(employee);
//		message.setInfoMessage("Successfully added employee");
//		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	@GetMapping("EmployeeDetailsServlet")
	public Employee employeeDetails(@Param(EMPLOYEE_ID) int employeeId) throws ServiceException, ValidationException {
		return employeeService.getEmployee(employeeId);
	}

	@GetMapping("LeaveBalanceServlet")
	public LeaveBalance leaveBalance(HttpServletRequest request, HttpServletResponse response)
			throws ValidationException, ServiceException {
		HttpSession session = request.getSession();
		int employeeId = (Integer) session.getAttribute(EMPLOYEE_ID);
		return leaveBalanceService.findLeaveBalance(employeeId);
	}

	@GetMapping("RemoveEmployeeServlet")
	public boolean remove(@Param(EMPLOYEE_ID) int employeeId)
			throws ValidationException, ServiceException {
//		Message message = new Message();
		return employeeService.removeEmployee(employeeId);
//		if (isRemoved) {
//			message.setInfoMessage("Successfully removed employee");
//		}
//		return new ResponseEntity<>(message, HttpStatus.OK);
	}
}