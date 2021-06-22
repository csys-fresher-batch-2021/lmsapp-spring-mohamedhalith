package in.mohamedhalith.exception;

public class DBException extends Exception {

	private static final long serialVersionUID = 1L;

	public DBException(Exception e, String message) {
		super(message, e);
	}
	
	public DBException(String message) {
		super(message);
	}
}
