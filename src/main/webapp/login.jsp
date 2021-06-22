<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<style>
		main {
			margin-top: 10%;
		}
		.float-child {
			width: 50%;
			float: left;
		}
		.container {
			border: 5px ridge lightblue;
		}
	</style>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<main class="container col-3">
		<h3 class="text-center h3">Login</h3>
		<jsp:include page="message.jsp"></jsp:include>
		<div class="form-group">
			<form onsubmit="login()">
				<label>Username</label>
				<div class="input-group" style="padding:5px;">
					<input type="text" name="username" id="username" placeholder="Enter username" class="form-control" pattern="[A-Za-z0-9]{7,30}"
					 autofocus required/>
				</div>
				<label class="control-label" for="email">
					Password
				</label>
				<div class="input-group" style="padding:5px;">
					<input type="password" name="password" id="password" class="form-control" pattern="[A-Za-z0-9]{8,30}" placeholder="Enter password"
					 required/>
					<div class="input-group-addon">
						<span class="fas fa-eye-slash" id="eyefull"></span>
					</div>
				</div>
				<div class="form-group" style="padding:5px;">
					<input type="radio" value="manager" name="role" id="admin" class="form-radio" style="padding:5px;" required/>
					<label for="admin">Admin</label>
					<input type="radio" value="employee" name="role" id="employee" class="form-radio"required/>
					<label for="employee">Employee</label>
				</div>
				<div class="float-container" style="padding:5px;">
					<button type="submit" class="btn btn-primary">Submit</button>
					<button type="reset" class="btn btn-danger">Reset</button>
				</div>
			</form>
		</div>
	</main>
	<script src="js/login-component.js"></script>
</body>
</html>