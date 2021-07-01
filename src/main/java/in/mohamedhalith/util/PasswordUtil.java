package in.mohamedhalith.util;

import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {
		
	public String generatePassword(String name,int employeeId) {
		String firstPart = name.substring(0, 4).toLowerCase();
		String secondPart = String.valueOf(employeeId);
		StringBuilder password = new StringBuilder();
		password.append(firstPart).append(secondPart);
		return password.toString();
	}

}
