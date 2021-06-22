package in.mohamedhalith.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LeaveRequest {

	private int leaveId;

	private Employee employee;

	private LocalDate fromDate;
	private LocalDate toDate;
	private int duration;
	private String type;
	private String reason;
	private String status;
	private LocalDateTime modifiedTime;
	private LocalDateTime appliedTime;
}
