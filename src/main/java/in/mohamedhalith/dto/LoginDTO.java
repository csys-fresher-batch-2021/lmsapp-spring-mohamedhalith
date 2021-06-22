package in.mohamedhalith.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDTO {
	
	private String username;
	private String password;
	private String role;

}
