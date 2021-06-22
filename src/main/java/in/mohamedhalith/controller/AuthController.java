package in.mohamedhalith.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.mohamedhalith.dto.AuthDTO;
import in.mohamedhalith.dto.LoginDTO;
import in.mohamedhalith.exception.ServiceException;
import in.mohamedhalith.exception.ValidationException;
import in.mohamedhalith.model.Employee;
import in.mohamedhalith.service.EmployeeService;
import in.mohamedhalith.validator.LoginValidator;

@RestController
public class AuthController {
	@Autowired
	EmployeeService employeeService;
	@Autowired
	LoginValidator loginValidator;
	@PostMapping("LoginServlet")
	public AuthDTO login(@RequestBody LoginDTO user,HttpServletRequest request) throws ServiceException, ValidationException {
		boolean validUser = loginValidator.verifyCredentials(user);
		AuthDTO loggedIn = null;
		int employeeId;
		if (validUser) {
			employeeId = employeeService.getEmployeeId(user.getUsername());
			Employee employee = employeeService.getEmployee(employeeId);
			loggedIn = new AuthDTO(employee.getName(),employeeId,user.getRole());
			HttpSession session = request.getSession();
			session.setAttribute("ROLE", user.getRole());
			session.setAttribute("LOGGEDIN_USERNAME", user.getUsername());
			session.setAttribute("employee", employee);
			session.setAttribute("employeeId", employee.getEmployeeId());
			return loggedIn;
		}
		throw new ValidationException("Invalid credentials");
		
	}

	@GetMapping("LogoutServlet")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		response.sendRedirect("login.jsp");
	}
}
