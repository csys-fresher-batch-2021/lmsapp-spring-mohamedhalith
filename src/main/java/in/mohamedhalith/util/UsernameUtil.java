package in.mohamedhalith.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import in.mohamedhalith.model.Employee;

@Component
public class UsernameUtil {

	public String generateUsername(String name,int employeeId) {
		String firstPart = String.valueOf(employeeId);
		String secondPart = name.substring(0, 4).toLowerCase();
		StringBuilder username = new StringBuilder();
		username.append(firstPart).append(secondPart);
		return username.toString();
	}
	
	public List<Employee> generateUsername(List<Employee> employeeList) {
		List<Employee> employees = new ArrayList<>();
		for (Employee employee : employeeList) {
			employee.setUsername(generateUsername(employee.getName(), employee.getEmployeeId()));
			employees.add(employee);
		}
		return employees;
	}
}
