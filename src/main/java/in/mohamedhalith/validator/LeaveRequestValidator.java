package in.mohamedhalith.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.mohamedhalith.dao.LeaveRequestDAO;
import in.mohamedhalith.exception.ServiceException;
import in.mohamedhalith.exception.ValidationException;
import in.mohamedhalith.model.LeaveBalance;
import in.mohamedhalith.model.LeaveRequest;
import in.mohamedhalith.service.LeaveBalanceService;
import in.mohamedhalith.service.LeaveRequestService;
import in.mohamedhalith.util.DateValidator;

@Component
public class LeaveRequestValidator {

	private LeaveRequestValidator() {
		// Default constructor
	}

	@Autowired
	LeaveBalanceService leaveBalanceService;
	@Autowired
	LeaveRequestService leaveRequestService;
	@Autowired
	LeaveRequestDAO leaveRequestDAO;

	/**
	 * This method checks given request is valid or not. Valid in the sense that
	 * there is no similar or duplicate requests present and the leave request is
	 * not in violation of allowed leaves.
	 * 
	 * @param leaveRequest
	 * @param employee
	 * @param employeeRequests
	 * @throws ValidationException
	 * @throws ServiceException
	 */
	public void isValidRequest(LeaveRequest leaveRequest, int employeeId) throws ValidationException, ServiceException {
		LeaveBalance leaveBalance = leaveBalanceService.findLeaveBalance(employeeId);
		isValidDates(leaveRequest);
		findDuplicateRequest(leaveRequest);
		isValidDuration(leaveRequest, leaveBalance);
	}

	/**
	 * This method is used to check whether the leave dates are valid or not
	 * 
	 * @param leaveRequest
	 * @throws ValidationException
	 */
	public static void isValidDates(LeaveRequest leaveRequest) throws ValidationException {
		DateValidator.isValidDate(leaveRequest.getFromDate());
		DateValidator.isValidDate(leaveRequest.getToDate());
	}

	public void isValidId(int leaveId) throws ValidationException, ServiceException {
		boolean valid = false;
		LeaveRequest leaveRequest = leaveRequestService.getLeaveRequest(leaveId);
		if (leaveRequest != null) {
			valid = true;
		}
		if (!valid) {
			throw new ValidationException("Cannot find leave request for given id");
		}

	}

	/**
	 * This method is used to find any duplicate request present in the request
	 * list.
	 * 
	 * @param leaveRequest
	 * @throws ValidationException
	 */
	public void findDuplicateRequest(LeaveRequest leaveRequest) throws ValidationException {
		Integer id = leaveRequestDAO.isExistingDate(leaveRequest);
		if (id != 0) {
			throw new ValidationException("Leave request found for mentioned date(s).");
		}
	}

	/**
	 * This method is used to verify whether the applied leave duration is within
	 * the permitted leave days.
	 * 
	 * @param leaveRequest
	 * @param leaveBalance
	 * @throws ValidationException
	 */
	public static void isValidDuration(LeaveRequest leaveRequest, LeaveBalance leaveBalance)
			throws ValidationException {
		boolean invalid = false;
		int duration = leaveRequest.getDuration();
		switch (leaveRequest.getType()) {
		case "SickLeave":
			if (duration > leaveBalance.getSickLeave()) {
				invalid = true;
			}
			break;
		case "CasualLeave":
			if (duration > leaveBalance.getCasualLeave()) {
				invalid = true;
			}
			break;
		case "EarnedLeave":
			if (duration > leaveBalance.getEarnedLeave()) {
				invalid = true;
			}
			break;
		default:
			throw new ValidationException("Invalid leave type");
		}

		if (invalid) {
			throw new ValidationException("Leave duration exceeds the permitted leave days");
		}
	}
}
