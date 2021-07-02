package in.mohamedhalith.controller;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import in.mohamedhalith.constant.FieldConstants;
import in.mohamedhalith.csv.EmployeeBean;
import in.mohamedhalith.csv.EmployeeCsv;
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
	@Autowired
	EmployeeCsv employeeCsv;

	private static final String EMPLOYEE_ID = FieldConstants.EMPLOYEE_ID;

	@GetMapping("ListEmployeeServlet")
	public Iterable<Employee> listEmployee() throws ServiceException {
		return employeeService.getEmployeeList();
	}

	@PostMapping("AddEmployeeServlet")
	public boolean addEmployee(@RequestBody Employee employee)
			throws ServiceException, ValidationException {
		return employeeService.addEmployee(employee);
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
		return employeeService.removeEmployee(employeeId);
	}

	@PostMapping("import/csv")
	public List<String> importFile(@RequestParam("file") MultipartFile file) throws ServiceException, ValidationException {
		List<String> importEmployees = null;
		if (!file.isEmpty()) {
			try {
				// Transferring the file obtained to a familiar path
				file.transferTo(Paths.get("D:\\files\\" + file.getOriginalFilename()));
				// Converting Comma Separated Values into EmployeeBean object 
				List<EmployeeBean> employeeList = employeeCsv.readFile("D:\\files\\" + file.getOriginalFilename());
				// Adding the employees into the records
				importEmployees = employeeService.importEmployees(employeeList);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
		return importEmployees;
	}
}