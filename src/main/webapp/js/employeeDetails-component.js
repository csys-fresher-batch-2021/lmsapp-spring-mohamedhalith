/**
 * 
 */
let params = window.location.search;
/**
* This function obtains details for the given employee id
*/
function getDetails() {
	let url = "EmployeeDetailsServlet" + params;
	let content = "";
	fetch(url).then(res => res.json()).then(res => {
		let employee = res;
		console.log(res);
		content += "<tr><th>Name</th><td>" + employee.name + "</td></tr>" +
			"<tr><th>Employee Id</th><td>" + employee.employeeId + "</td></tr>" +
			"<tr><th>Mobile Number</th><td>" + employee.mobileNumber + "</td></tr>" +
			"<tr><th>Email</th><td>" + employee.email + "</td></tr>";
		document.querySelector("#table-body").innerHTML = content;
	});
}
getDetails();
/**
* This function confirms whether the client intend to do the action
*/
function confirmAction() {
	let answer = window.confirm("Do you want to remove this employee");
	if (answer) {
		removeEmployee();
	} else {
		console.log("Cancelled the operation");
	}
}
/**
* This function sends parameters through url and obtains the result given by the
* servlet.
*/
function removeEmployee() {
	let url = "RemoveEmployeeServlet" + params;
	fetch(url).then(res => res.json()).then(res => {
		let result = res;
		if (result) {
			toastr.success("Successfully removed");
			setTimeout(function() {
				window.location.href = "listemployees.jsp";
			},1500);
		} else {
			toastr.error("Failed to remove");
		}
	}).catch(err => {
		console.log(err.data);
	});
}