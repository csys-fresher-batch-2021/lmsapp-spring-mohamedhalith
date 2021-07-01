package in.mohamedhalith.util;

import org.springframework.stereotype.Component;

@Component
public class UsernameUtil {

	public String generateUsername(String name,int employeeId) {
		String firstPart = String.valueOf(employeeId);
		String secondPart = name.substring(0, 4).toLowerCase();
		StringBuilder username = new StringBuilder();
		username.append(firstPart).append(secondPart);
		return username.toString();
	}
}
