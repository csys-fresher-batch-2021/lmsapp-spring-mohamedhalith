package in.mohamedhalith.exception;

public class ValidationException extends Exception{

	private static final long serialVersionUID = 1L;

	public ValidationException(Exception e,String message) {
		super(message,e);
	}
	
	public ValidationException(String message) {
		super(message);
	}
}
