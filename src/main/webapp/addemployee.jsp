<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<title>Add employee</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<jsp:include page="message.jsp"></jsp:include>
	<main class="container-fluid">
		<div class="row">
			<div class="col-sm">
				<h3>Add Employee</h3>
				<form onsubmit="add()">
				<label for="name">Employee Name</label>
				<input type="text" name = "name" id="name" placeholder="Employee name" pattern="[A-Za-z][A-Za-z ]{2,100}" 
					onkeyup="setUsername()" autofocus required/><br/>
				<label for="employeeId">Employee Id</label>
				<input type="text" name="employeeId" id="employeeId" placeholder="Employee ID" pattern="[0-9]{4}"
					 onkeyup="setUsername()" required/><br/>
				<label for="mobileNumber">Mobile Number</label>
				<span>+91 - </span>
				<input type="text" name="mobileNumber" id="mobileNumber" placeholder="Mobile Number" pattern="[0-9]{10}"
					 required/><br/>
				<label for="email">Email</label>
				<input type="email" name="email" id="email" placeholder="Email" required/><br/>
				<label for="username">Username</label>
				<input type="text" name="username" id="username" placeholder="Username" pattern="[A-Za-z0-9]+" minlength="7"
					 required/><br/>
				<label for="password">Password</label>
				<input type="text" name="password" id="password" placeholder="Password" pattern="[A-Za-z0-9]+" minlength="8"
					 required/><br/>
				<label for="joinedDate">Joined Date</label>
				<input type="date" name="joinedDate" id="joinedDate"/><br/>
				<input type="submit" class="btn btn-success" value="Add"/>
				<input type="reset" class="btn btn-danger"/>
				</form>
			</div>
			<div class="col-sm">
				<h3>Import employees</h3>
				<form id="form" enctype="multipart/form-data" onsubmit="upload()">
					<input type="file" name="file" id="file" accept=".csv,.xlsx"/>
					<input type="submit" id="submitBtn" name="submitBtn"/>
				</form>
				<p><i>*If uploaded csv files contain any errors,a text file containing error details
					 can be	downloaded</i></p>
			</div>
		</div>
	</main>
	<script src="js/addemployee-component.js"></script>
</body>
</html>