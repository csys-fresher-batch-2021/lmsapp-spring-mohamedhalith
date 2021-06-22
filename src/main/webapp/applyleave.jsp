<%@page import="in.mohamedhalith.model.Employee"%>
<%@page import="in.mohamedhalith.service.EmployeeService"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<title>Apply for Leave</title>
</head>
<body>
<c:set var="loggedInEmployee" value="${sessionScope.employee }"></c:set>
<jsp:include page="header.jsp"></jsp:include>
	<main class="container-fluid">
		<div class="row">
			<div class="col-sm">
				<h4>Apply for Leave</h4>
				<jsp:include page="message.jsp"></jsp:include>
				<form onsubmit="apply()">
					<label>Employee Name</label>
					<input type="text" name="employeeName" id="employeeName" value="${loggedInEmployee.getName()}" 
						readonly required/><br/>
						
					<label>Employee Id</label>
					<input type="text" name="employeeId" id="employeeId" value="${loggedInEmployee.getEmployeeId()}"
						readonly required/><br/>
						
					<label>From Date</label>
					<input type="date" name="fromDate" id="fromDate" required /><br/>
					
					<label>To Date</label>
					<input type="date" name="toDate" id="toDate" required oninput="findDuration()"/> <br/>
					
					<label>Leave Duration</label>
					<input type="text" name="duration" id="duration" readonly/><br/>
					
					<label>Type</label>
					<select name="leaveType" id="leaveType" required>
					<option value="" disabled selected>-----SELECT LEAVE TYPE-----</option>
					<option value="SickLeave">Sick Leave</option>
					<option value="CasualLeave">Casual Leave</option>
					<option value="EarnedLeave">Earned Leave</option>
					</select><br/>
					
					<label>Reason</label>
					<textarea name="reason" id="reason" required></textarea><br/>
					<input type="submit">
				</form>
			</div>
			<div class="col-sm">
				<h4>LeaveBalance</h4>
				<jsp:include page="leavebalance.jsp"></jsp:include>
			</div>
		</div>
	</main>
<script src="js/applyleave-component.js">
	
</script>
</body>
</html>