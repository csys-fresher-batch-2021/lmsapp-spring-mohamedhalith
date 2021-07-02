package in.mohamedhalith.csv;

import java.time.LocalDate;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

import lombok.Data;

@Data
public class EmployeeBean {
	
	@CsvBindByPosition(position = 0 , required = true)
	private int employeeId;
	@CsvBindByPosition(position = 1 , required = true)
	private String name;
	
	@CsvBindByPosition(position = 2 , required = true)
	private long mobileNumber;
	@CsvBindByPosition(position = 3 , required = true)
	private String email;
	
	@CsvDate(value = "dd-MM-yyyy")
	@CsvBindByPosition(position = 4 , required = true)
	private LocalDate joinedDate;
	
}
