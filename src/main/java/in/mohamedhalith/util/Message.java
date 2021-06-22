package in.mohamedhalith.util;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Data;

@Data
public class Message {

	private String errorMessage;
	private String infoMessage;
	private Map<String, String> errors = new LinkedHashMap<>();
}
