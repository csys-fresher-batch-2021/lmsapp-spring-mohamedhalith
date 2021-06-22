<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<title>Approve/Reject</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<main class="container-fluid">
		<h3>Leave Requests</h3>
		<input type="radio" name="filter" id="all" onclick="getRequests(null)" />
		<label for="all">All</label>
		 <input type="radio" name="filter" id="pending"	onclick="getRequests('waiting for approval')" /> 
		<label for="pending">Pending</label>
		<input type="radio" name="filter" id="cancelled" onclick="getRequests('cancelled')" />
		<label for=cancelled>Cancelled</label>
		<input type="radio" name="filter" id="approved" onclick="getRequests('approved')" />
		<label for="approved">Approved</label> 
		<input type="radio" name="filter" id="rejected" onclick="getRequests('rejected')" /> 
		<label for="rejected">Rejected</label> <br />
		<span style="color: green" id="message"></span>
		<table id="requestTable" class="table table-bordered">
			<caption>All leave requests</caption>
			<thead id="table-head">
				<tr>
					<th id="serial">S.No</th>
					<th id="employeeId">Emp. Id</th>
					<th id="name">Name</th>
					<th id="fromDate">From Date</th>
					<th id="toDate">To Date</th>
					<th id="duration">Duration</th>
					<th id="reason">Reason</th>
					<th id="status">Status</th>
					<th id="appliedTime">Applied at</th>
					<th colspan = "2"id="action"></th>
				</tr>
			</thead>
			<tbody id="table-body">

			</tbody>
		</table>
	</main>
	<script src="js/viewrequests-component.js"></script>
</body>
</html>