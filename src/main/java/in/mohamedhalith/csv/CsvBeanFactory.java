package in.mohamedhalith.csv;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Component
public abstract class CsvBeanFactory<T> {

	public List<T> read(String fileName,Class className) {
		List<T> list = new ArrayList<>();
		
		try {
			Reader reader = Files.newBufferedReader(Paths.get(fileName));
			
			CsvToBean csvReader = new CsvToBeanBuilder(reader).withType(className)
					.withIgnoreLeadingWhiteSpace(true).build();
			
			for(T user: (Iterable<T>) csvReader) {
				list.add(user);
			}
			
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}
