package in.mohamedhalith.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import in.mohamedhalith.implementation.LeaveBalanceImpl;

/**
 * A scheduler class used to execute an operation 
 * @author moha2627
 *
 */
@Component
public class LeaveBalanceScheduler {

	@Autowired
	LeaveBalanceImpl leaveBalanceRepo;

	/**
	 * This method is a scheduled method which is used to update (increase) the
	 * leave balance of an employee according the policy of the organization.
	 * 
	 * <p>
	 * This method is executed at 11:50:00 PM (23:50:00) on last day of every month
	 */
	@Scheduled(cron = "0 50 23 L * *")
	public void updateLeaveBalance() {
		leaveBalanceRepo.allocateLeaveBalance();
	}
}
