package in.mohamedhalith.util;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Message {

	@NonNull
	private String errorMessage;
	private String infoMessage;
	private Map<String, String> errors = new LinkedHashMap<>();
}
