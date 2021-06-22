<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<title>Employee Details</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<main class="container-fluid">
		<h3>Employee Details</h3>
		<table class="table table-bordered">
			<caption>Employee's details</caption>
			<thead class="table table-primary">
				<tr>
					<th id="category">Category</th>
					<th id="details">Details</th>
				</tr>
			</thead>
			<tbody id="table-body">
			</tbody>
		</table>
		<button onclick="confirmAction()" class="btn btn-danger">Remove</button>
	</main>
	<script src="js/employeeDetails-component.js"></script>
</body>
</html>