package in.mohamedhalith.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthDTO {
	
	private String name;
	private int employeeId;
	private String role;

}
