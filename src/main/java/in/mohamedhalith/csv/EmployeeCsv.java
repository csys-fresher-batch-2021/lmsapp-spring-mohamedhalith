package in.mohamedhalith.csv;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class EmployeeCsv extends CsvBeanFactory<EmployeeBean> {

	public List<EmployeeBean> readFile(String fileName) {
		return read(fileName,EmployeeBean.class);
	}
}
