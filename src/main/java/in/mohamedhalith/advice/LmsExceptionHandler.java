package in.mohamedhalith.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import in.mohamedhalith.exception.ServiceException;
import in.mohamedhalith.exception.ValidationException;
import in.mohamedhalith.util.Message;

@RestControllerAdvice
public class LmsExceptionHandler {
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<Message> handleValidationException(ValidationException e) {
		Message message = new Message(e.getMessage());
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<Message> handleServiceException(ServiceException e) {
		Message message = new Message(e.getMessage());
		return new ResponseEntity<>(message,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	

}
