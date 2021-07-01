package in.mohamedhalith.dto;

import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDTO {

	@Pattern(regexp = "[A-Za-z0-9]{7,30}", message = "Invalid username")
	private String username;
	
	@Pattern(regexp = "[A-Za-z0-9]{8,30}", message = "Invalid password")
	private String password;

	private String role;

}
