package in.mohamedhalith.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.mohamedhalith.constant.UpdateAction;
import in.mohamedhalith.exception.ValidationException;

public class StringValidator {

	private StringValidator() {
		// Default Constructor
	}

	private static final String INVALIDUSERNAME = "Invalid username";
	private static final String INVALIDEMAIL = "Invalid email";
	private static final String INVALIDPASSWORD = "Invalid password";
	private static final String INVALIDNAME = "Invalid employee name";

	public static void isValidString(String string) throws ValidationException {
		if (string == null || string.trim().isEmpty()) {
			throw new ValidationException("String should not be null or empty");
		}
	}

	/**
	 * This method is used to validate the given password and return true if it
	 * satifies the regular expression.
	 * 
	 * @param password
	 * @throws ValidationException
	 */
	public static void isValidPassword(String password) throws ValidationException {
		// Regex pattern - the word can be either block or small letters or digits
		// Also its length must be minimum 8 characters
		String regex = "[A-Za-z0-9]{8,}";
		try {
			StringValidator.isValidString(password);
			Pattern pattern = Pattern.compile(regex);
			Matcher match = pattern.matcher(password);
			boolean valid = match.matches();
			if (!valid) {
				throw new ValidationException(INVALIDPASSWORD);
			}
		} catch (ValidationException e) {
			throw new ValidationException(e, INVALIDPASSWORD);
		}

	}

	/**
	 * This method is used to validate the given username and returns true if it
	 * matches the required pattern.
	 * 
	 * @param username
	 * @throws ValidationException
	 */
	public static void isValidUsername(String username) throws ValidationException {
		// Regex pattern - the word can be either block or small letters or digits
		// Also its length must be minimum 8 characters
		String regex = "[A-Za-z0-9]{7,}";
		try {
			StringValidator.isValidString(username);
			Pattern pattern = Pattern.compile(regex);
			Matcher match = pattern.matcher(username);
			boolean valid = match.matches();
			if (!valid) {
				throw new ValidationException(INVALIDUSERNAME);
			}
		} catch (ValidationException e) {
			throw new ValidationException(e, INVALIDUSERNAME);
		}
	}

	/**
	 * This method is used to validate the given email and returns true if it
	 * matches the required pattern.
	 * 
	 * @param username
	 * @throws ValidationException
	 */
	public static void isValidEmail(String email) throws ValidationException {
		String regex = "^([a-z0-9-_\\.]+)@([a-z0-9]+)\\.([a-z\\.]+)$";
		try {
			StringValidator.isValidString(email);
			Pattern pattern = Pattern.compile(regex);
			Matcher match = pattern.matcher(email);
			boolean valid = match.matches();
			if (!valid) {
				throw new ValidationException(INVALIDEMAIL);
			}
		} catch (ValidationException e) {
			throw new ValidationException(e, INVALIDEMAIL);
		}
	}

	/**
	 * This method is used to validate the given string and returns true if it
	 * matches the required pattern.
	 * 
	 * @param username
	 * @throws ValidationException
	 */
	public static void isValidAction(String action) throws ValidationException {
		boolean isValid = false;
		if (action.equalsIgnoreCase(UpdateAction.APPROVE.toString())
				|| action.equalsIgnoreCase(UpdateAction.CANCEL.toString())
				|| action.equalsIgnoreCase(UpdateAction.REJECT.toString())) {
			isValid = true;
		}

		if (!isValid) {
			throw new ValidationException("Invalid Action");
		}
	}

	public static void isValidName(String name) throws ValidationException {
		// Regex - may be alphabets also can include whitespaces
		String regex = "[A-Za-z\\s]+";
		try {
			StringValidator.isValidString(name);
			Pattern pattern = Pattern.compile(regex);
			Matcher match = pattern.matcher(name);
			boolean valid = match.matches();
			if (!valid) {
				throw new ValidationException(INVALIDNAME);
			}
		} catch (ValidationException e) {
			throw new ValidationException(e, INVALIDNAME);
		}

	}
}
