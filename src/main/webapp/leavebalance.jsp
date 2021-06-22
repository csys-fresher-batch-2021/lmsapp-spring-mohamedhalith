<%@page import="in.mohamedhalith.model.LeaveBalance"%>
<%@page import="in.mohamedhalith.model.Employee"%>
<%@page import="in.mohamedhalith.service.EmployeeService"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<title>Leave Balance</title>
</head>
<body>
<c:set var="employeeid" value="${sessionScope.employeeId }"></c:set>
	<main class="container-fluid">
<%-- 	<input type="hidden" value="${employeeid }" id="employeeId"/>
 --%>		<table class="table table-bordered">
			<caption>Remaining Leaves</caption>
			<thead class="table-primary">
				<tr>
					<th id="name">Name</th>
					<th id="employeeid">Emp. Id</th>
					<th id="sickleave">Sick Leave</th>
					<th id="casualleave">Casual Leave</th>
					<th id="earnedleave">Earned Leave</th>
				</tr>
			</thead>
			<tbody id="table-body">
				
			</tbody>
		</table>
	</main>
	<script src="js/leavebalance-component.js"></script>
</body>
</html>