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
	<main class="container-fluid">
		<h3>Request Status</h3>
		<input type="radio" name="filter" id="all" onclick="getAllRequests(null)" />
		<label for="all">All</label>
		 <input type="radio" name="filter" id="pending"	onclick="getAllRequests('waiting for approval')" /> 
		<label for="pending">Pending</label>
		<input type="radio" name="filter" id="cancelled" onclick="getAllRequests('cancelled')" />
		<label for=cancelled>Cancelled</label>
		<input type="radio" name="filter" id="approved" onclick="getAllRequests('approved')" />
		<label for="approved">Approved</label> 
		<input type="radio" name="filter" id="rejected" onclick="getAllRequests('rejected')" /> 
		<label for="rejected">Rejected</label> <br />
		<span style="color: green" id="message"></span>
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
					<th id="action"></th>
				</tr>
			</thead>
			<tbody id="table-body">

			</tbody>
		</table>
	</main>
	<script src="js/viewrequest-json-component.js">	</script>
</body>
</html>