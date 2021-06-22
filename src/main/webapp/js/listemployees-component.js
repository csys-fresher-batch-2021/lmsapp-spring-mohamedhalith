/**
 * This method is used to get list of employees
 */
function getEmployees() {
	let url = "ListEmployeeServlet";
	let content = "";
	let val = document.querySelector("#alphabetical").value;
	let searchKey = document.querySelector("#search").value.trim();
	fetch(url).then(res => res.json()).then(res => {
		let employees = res;
		let serial = 1;
		if (val != null) {
			employees = sortEmployee(employees, val);
		}
		if (search != null) {
			employees = search(employees, searchKey);
		}
		for (let employee of employees) {
			content += "<tr>" +
				"<td>" + serial + "</td>" +
				"<td>" + employee.employeeId + "</td>" +
				"<td>" + employee.name + "</td>" +
				"<td><a class=\"btn btn-info\" href=\"employeeDetails.jsp?employeeId=" + employee.employeeId + "\">View</a></td>" +
				"</tr>";
			serial += 1;
		}
		document.querySelector("#table-body").innerHTML = content;
	}).catch(err=>{
		console.log(err);
	})
}
getEmployees();

function sortEmployee(res, value) {
	res.sort(function(a, b) {
		a = a.name.toLowerCase();
		b = b.name.toLowerCase();
		let sortValue = -1;
		if(a>b){
			sortValue = 1;
		}else if(a==b){
			sortValue = 0;
		}
		return sortValue;
	})
	if (value === "z") {
		res = res.reverse();
	}
	return res;
}
function search(res, value) {
	res = res.filter(result => result.name.toLowerCase().includes(value.toLowerCase()) ||
		result.employeeId.toString().includes(value.toLowerCase()));
	return res;
}