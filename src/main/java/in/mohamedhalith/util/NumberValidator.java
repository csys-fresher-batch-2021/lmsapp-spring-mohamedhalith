package in.mohamedhalith.util;

import in.mohamedhalith.exception.ValidationException;

public class NumberValidator {

	private NumberValidator() {
		// Default Constructor
	}
	/**
	 * This method is used to check whether the provided number is valid or not
	 * 
	 * @param mobileNumber
	 * @throws ValidationException
	 */
	public static void isValidMobileNumber(long mobileNumber) throws ValidationException {
		String mobile = String.valueOf(mobileNumber);
		if (mobile.length() != 10) {
			throw new ValidationException("Invalid mobile number");
		}
	}

	/**
	 * This method is used to check whether the provided employee id matches the
	 * company's employee id code
	 * 
	 * @param employeeId
	 * @throws ValidationException
	 */
	public static void isValidEmployeeId(int employeeId) throws ValidationException {
		String id = String.valueOf(employeeId);
		if (id.length() != 4) {
			throw new ValidationException("Invalid Employee Id");
		}
	}
}
