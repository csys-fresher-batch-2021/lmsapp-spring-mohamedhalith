package in.mohamedhalith.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.mohamedhalith.constant.FieldConstants;
import in.mohamedhalith.dao.LeaveRequestDAO;
import in.mohamedhalith.exception.ServiceException;
import in.mohamedhalith.exception.ValidationException;
import in.mohamedhalith.model.LeaveRequest;
import in.mohamedhalith.service.LeaveRequestService;

@RestController
public class LeaveRequestController {

	@Autowired
	LeaveRequestService leaveRequestService;
	@Autowired
	LeaveRequestDAO leaveRequestRepo;

	private static final String EMPLOYEE_ID = FieldConstants.EMPLOYEE_ID;
	private static final String LEAVE_ID = FieldConstants.LEAVE_ID;
		

	@PostMapping("ApplyLeaveServlet")
	public boolean applyLeave(@RequestBody LeaveRequest leaveRequest, HttpServletRequest request,
			HttpServletResponse response) throws ServiceException, ValidationException {
		HttpSession session = request.getSession();
		int employeeId = (Integer) session.getAttribute(EMPLOYEE_ID);
		
		// Sending to the backend manager
		return leaveRequestService.applyLeaveRequest(leaveRequest, employeeId);
	}

	@GetMapping("ApproveRejectServlet")
	public boolean approveOrReject(@Param(LEAVE_ID) int leaveId, @Param(EMPLOYEE_ID) int employeeId,
			@Param("action") String action) throws ValidationException, ServiceException {
		return leaveRequestService.updateLeaveRequest(action, leaveId);
	}

	@GetMapping("CancelRequestServlet")
	public boolean cancel(HttpServletRequest request, HttpServletResponse response)
			throws ServiceException, ValidationException {
		HttpSession session = request.getSession();
		int employeeId = (Integer) session.getAttribute(EMPLOYEE_ID);
		int leaveId = Integer.parseInt(request.getParameter(LEAVE_ID));
		return leaveRequestService.cancelLeaveRequest(leaveId, employeeId);
	}

	@GetMapping("ListAllRequestsServlet")
	public List<LeaveRequest> listAll() throws ServiceException {
		return leaveRequestService.getRequestList();
	}

	@GetMapping("RequestStatusServlet")
	public List<LeaveRequest> getStatus(HttpServletRequest request, HttpServletResponse response)
			throws ValidationException, ServiceException {
		HttpSession session = request.getSession();
		int employeeId = (Integer) session.getAttribute(EMPLOYEE_ID);
		return leaveRequestService.getEmployeeRequests(employeeId);
	}
	
}