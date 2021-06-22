package in.mohamedhalith.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@JsonInclude(Include.NON_NULL)
@Data
@Table(value = "employees")
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Name cannot be empty")
	private String name;

	@Id
	private int id;

	@NotNull(message = "Employee Id cannot be null")
	@Column("employee_id")
	private int employeeId;

	@Column("mobile_number")
	private long mobileNumber;

	@Email(message = "Please enter valid email")
	private String email;

	private String username;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	private String role;

	@Column("active")
	private boolean status;

	@Column("joined_date")
	private LocalDate joinedDate;

	@Column("modified_time")
	private LocalDateTime modifiedTime;

}
