package in.mohamedhalith.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import in.mohamedhalith.csv.EmployeeBean;
import in.mohamedhalith.model.Employee;

@Component
public class EmployeeBeanConverter {

	public Employee toEmployee(EmployeeBean employeeBean) {
		Employee employee = new Employee();
		employee.setName(employeeBean.getName());
		employee.setEmployeeId(employeeBean.getEmployeeId());
		employee.setMobileNumber(employeeBean.getMobileNumber());
		employee.setEmail(employeeBean.getEmail());
		employee.setJoinedDate(employee.getJoinedDate());
		return employee;
	}
	
	public List<Employee> toEmployee(List<EmployeeBean> employeeBeans) {
		List<Employee> employeeList = new ArrayList<>();
		for (EmployeeBean employeeBean : employeeBeans) {
			employeeList.add(toEmployee(employeeBean));
		}
		return employeeList;
	}
}
