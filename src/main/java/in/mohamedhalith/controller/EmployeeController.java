package in.mohamedhalith.controller;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import in.mohamedhalith.util.Message;

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
	public ResponseEntity<Message> addEmployee(@RequestBody Employee employee)
			throws ServiceException, ValidationException {
		Message message = new Message();
		employeeService.addEmployee(employee);
		message.setInfoMessage("Successfully added employee");
		return new ResponseEntity<>(message, HttpStatus.OK);
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
	public ResponseEntity<Message> remove(@Param(EMPLOYEE_ID) int employeeId)
			throws ValidationException, ServiceException {
		Message message = new Message();
		boolean isRemoved = employeeService.removeEmployee(employeeId);
		if (isRemoved) {
			message.setInfoMessage("Successfully removed employee");
		}
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	@PostMapping("import/csv")
	public void importFile(@RequestParam("file") MultipartFile file) {
		System.out.println(file.getOriginalFilename());
		if (!file.isEmpty()) {
			try {
				file.transferTo(Paths.get("D:\\files\\" + file.getOriginalFilename()));
				List<EmployeeBean> employeeList = employeeCsv.readFile("D:\\files\\" + file.getOriginalFilename());
				employeeService.addBulkEmployees(employeeList);
				
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Empty");
		}

//		try {
//			Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
//			
//			CsvToBean csvReader = new CsvToBeanBuilder(reader).withType(EmployeeBean.class)
//					.withIgnoreLeadingWhiteSpace(true).build();
//			
//			List<EmployeeBean> list = new ArrayList<>();
//			for(EmployeeBean user: (Iterable<EmployeeBean>) csvReader) {
//				list.add(user);
//			}
//			for (EmployeeBean employeeBean : list) {
//				System.out.println(employeeBean);
//			}
//		} catch (IllegalStateException | IOException e) {
//			e.printStackTrace();
//		}
	}
}