package in.mohamedhalith.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import in.mohamedhalith.model.Employee;

@Component
public class PasswordUtil {
		
	public String generatePassword(String name,int employeeId) {
		String firstPart = name.substring(0, 4).toLowerCase();
		String secondPart = String.valueOf(employeeId);
		StringBuilder password = new StringBuilder();
		password.append(firstPart).append(secondPart);
		return password.toString();
	}

	
	public List<Employee> generatePassword(List<Employee> employeeList) {
		List<Employee> employees = new ArrayList<>();
		for (Employee employee : employeeList) {
			employee.setPassword(generatePassword(employee.getName(),employee.getEmployeeId()));
			employees.add(employee);
		}
		return employees;
	}
}
