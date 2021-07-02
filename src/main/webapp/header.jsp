<%@page import="org.springframework.beans.factory.annotation.Autowired"%>
<%@page import="in.mohamedhalith.model.Employee"%>
<%@page import="in.mohamedhalith.service.EmployeeService"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<link rel="stylesheet" href="assets/css/bootstrap.min.css">
<link rel="stylesheet" href="assets/css/style.css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
	integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ"
	crossorigin="anonymous">
<link rel="stylesheet" href="assets/css/toastr.css">
<script src="assets/js/axios.min.js"></script>
<script src="assets/js/jquery-1.9.1.min.js"></script>
<script src="assets/js/jquery-migrate-1.2.1.min.js"></script>
<script src="assets/js/toastr.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/FileSaver.js/2.0.0/FileSaver.js"></script>
<script src="js/header-component.js"></script>
<header>
	<c:set var="username" value="${sessionScope.LOGGEDIN_USERNAME}"></c:set>
	<c:set var="role" value="${sessionScope.ROLE }"></c:set>
	<c:set var="employee" value="${sessionScope.employee }"></c:set>
	<c:set var="employeeId" value="${sessionScope.employeeId }"></c:set>
	<nav class="navbar navbar-expand-sm navbar-dark bg-dark">
		<a class="navbar-brand" href="index.jsp">Leave Management System</a>
		<button class="navbar-toggler d-lg-none" type="button"
			data-toggle="collapse" data-target="#collapsibleNavId"
			aria-controls="collapsibleNavId" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="collapsibleNavId">
			<ul class="navbar-nav mr-auto mt-2 mt-lg-0">
				<li class="nav-item active"><a class="nav-link"
					href="index.jsp">Home <span class="sr-only">(current)</span></a></li>


				<c:if
					test="${not empty username && not empty role && role.equalsIgnoreCase('manager') }">
					<li class="nav-item"><a class="nav-link"
						href="listemployees.jsp" >Employees</a></li>
					<li class="nav-item"><a class="nav-link"
						href="viewrequests.jsp" >View Requests</a></li>
					<li class="nav-item"><a class="nav-link"
						href="addemployee.jsp" >Add Employee</a></li>
				</c:if>
				<c:if
					test="${not empty username && not empty role && role.equalsIgnoreCase('employee') }">
					<li class="nav-item"><a class="nav-link"
						href="viewbalance.jsp">Leave Balance</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="applyleave.jsp"
						>Apply for Leave</a></li>
					<li class="nav-item"><a class="nav-link"
						href="viewrequeststatus-json.jsp">View
							Status</a></li>
				</c:if>
			</ul>
			<c:if test="${not empty username }">
				<ul class="navbar-nav ml-auto mt-2 mt-lg-0" >
					<li class="nav-item active"><a class="nav-link" href="#"
						>Welcome ${employee.getName()}</a></li>
					<li class="nav-item"><a class="nav-link" href="" onclick="logout()"
						>Logout</a></li>
				</ul>
			</c:if>
			<c:if test="${empty username}">
				<ul class="navbar-nav ml-auto mt-2 mt-lg-0">
					<li class="nav-item active"><a class="nav-link"
						href="login.jsp" >Login</a></li>
				</ul>
			</c:if>
		</div>
	</nav>
</header>