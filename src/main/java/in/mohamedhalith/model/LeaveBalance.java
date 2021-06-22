package in.mohamedhalith.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
@Data
public class LeaveBalance implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private Employee employee;
	private int sickLeave;
	private int casualLeave;
	private int earnedLeave;
	private LocalDateTime modifiedTime;

}
