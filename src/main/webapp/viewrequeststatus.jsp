<%@page import="java.time.LocalDate"%>
<%@page import="in.mohamedhalith.model.LeaveRequest"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<title>Request status</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<h3>Request Status</h3>
<%
String username = (String) session.getAttribute("LOGGEDIN_USERNAME");
List<LeaveRequest> employeeRequests = (List<LeaveRequest>) request.getAttribute("employeeRequests");
%>
	<main class="container-fluid">
		<table class="table table-bordered">
		<caption>Leave Requests</caption>
			<thead class="table-primary">
				<tr>
					<th id="serial">S.No</th>
					<th id="employeeId">Id</th>
					<th id="employeeName">Name</th>
					<th id="fromDate">From</th>
					<th id="toDate">To</th>
					<th id="duration">Duration</th>
					<th id="status">Status</th>
					<th id="appliedTime">Applied on</th>
				</tr>
			</thead>
			<tbody>
				<%
				int serial = 1;
				if(employeeRequests == null){
				%>
				<tr><td colspan="8" style="text-align:center">No leave request applied</td></tr>
				<%	
				}else{
				for(LeaveRequest leaveRequest : employeeRequests){
				%>
				<tr>
					<td><%= serial%></td>
					<td><%=leaveRequest.getEmployee().getEmployeeId() %></td>
					<td><%= leaveRequest.getEmployee().getName() %></td>
					<td><%= leaveRequest.getFromDate() %></td>
					<td><%= leaveRequest.getToDate() %></td>
					<td><%= leaveRequest.getDuration() %></td>
					<td><%= leaveRequest.getStatus() %></td>
					<%
					LocalDate date = leaveRequest.getAppliedTime().toLocalDate();
					%>
					<td><%= date %></td>
				</tr>
				<%
					}
					serial+=1;
				}
				%>
			</tbody>
		</table>
	</main>
</body>
</html>