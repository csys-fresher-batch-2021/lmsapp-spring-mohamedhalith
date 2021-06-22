<%@page import="in.mohamedhalith.service.EmployeeService"%>
<%@page import="in.mohamedhalith.model.Employee"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<title>Employees</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
	<main class="container-fluid">
		<h3>List Employees</h3>
		<div>
			<div style="float:left" class="form-group">
			<label for="alphabetical" class="input-group-prepend">Sort by</label>
			<select name="alphabetical" id="alphabetical" onchange="getEmployees()"  class="custom-select">
			<option value="a" selected>A-Z</option>
			<option value="z">Z-A</option>
			</select>
			</div>
			<div style="float:right" class="form-group">
			<input type="text" id="search" name="search" oninput="getEmployees()" placeholder="Search" class="form-control form-rounded">
			</div>
		</div>
		<table class="table table-bordered">
		<caption>List of Employees</caption>
		<thead>
		<tr>
		<th id="serial">S.No</th>
		<th id="id">Id</th>
		<th id="name">Name</th>
		<th id="viewbtn"></th>
		</thead>
		<tbody id="table-body">
		</tbody>
		</table>
	</main>
	<script src="js/listemployees-component.js"></script>
</body>
</html>