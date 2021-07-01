package in.mohamedhalith.csv;

import java.time.LocalDate;

import com.opencsv.bean.CsvBindByName;
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
	
//	@CsvBindByName(column = "employeeId")
//	private int employeeId;
//	@CsvBindByName(column = "name")
//	private String name;
//	
//	@CsvBindByName(column = "mobileNumber")
//	private long mobileNumber;
//	@CsvBindByName(column = "email")
//	private String email;
//	
//	@CsvDate(value = "dd-MM-yyyy")
//	@CsvBindByName(column = "joinedDate")
//	private LocalDate joinedDate;

}
