package in.mohamedhalith.util;

import java.time.LocalDate;

import in.mohamedhalith.exception.ValidationException;

public class DateValidator {

	private DateValidator() {
		// Default Constructor
	}

	/**
	 * This method is used to check the given date is not a past date.
	 * 
	 * @param date
	 * @throws ValidationException
	 */
	public static void isValidDate(LocalDate date) throws ValidationException {
		LocalDate today = LocalDate.now();
		if (date.isBefore(today)) {
			throw new ValidationException("Date cannot be past date");
		}
	}

	/**
	 * This method is used to validate the joined date of the employee. Checks for a
	 * particular condition and throws exception based on result
	 * 
	 * @param joinedDate
	 * @throws ValidationException
	 */
	public static void isValidJoinedDate(LocalDate joinedDate) throws ValidationException {
		LocalDate today = LocalDate.now();
		LocalDate minDate = today.minusDays(30);
		if (joinedDate.isAfter(today) || joinedDate.isBefore(minDate)) {
			throw new ValidationException("Invalid Joined Date");
		}
	}
}
