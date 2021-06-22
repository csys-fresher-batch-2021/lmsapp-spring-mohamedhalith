/**
 * This method is used to get leave request of employee
 */
function getRequests(arg) {
	let url = "ListAllRequestsServlet";
	/**
	 * This function fetches data from the servlet and displays the response from the servlet
	 */
	fetch(url).then(res => res.json()).then(res => {
		console.log(res);
		let requests = res;
		let serial = 1;
		let content = "";
		for (let request of requests) {
			if (arg === null || request.status === arg) {
				content += "<tr><td>" + serial + "</td>"
					+ "<td>" + request.employee.employeeId + "</td>"
					+ "<td>" + request.employee.name + "</td>"
					+ "<td>" + request.fromDate + "</td>"
					+ "<td>" + request.toDate + "</td>"
					+ "<td>" + request.duration + "</td>"
					+ "<td>" + request.reason + "</td>"
					+ "<td>" + request.status + "</td>"
					+ "<td>" + request.appliedTime + "</td>"
				serial += 1;
			}

			if (arg === 'waiting for approval' && arg === request.status) {
				content +=
					"<td><button class=\"btn btn-success\" onclick=\"update('approve'," + request.leaveId + "," + request.employee.employeeId + ")\">Approve</button></td>"
					+ "<td><button class=\"btn btn-danger\" onclick=\"update('reject'," + request.leaveId + "," + request.employee.employeeId + ")\">Reject</button></td></tr>"
			} else {
				content += "</tr>";
			}
		}
		document.querySelector("#table-body").innerHTML = content;
	});
}
getRequests(null);
/**
 * This function is used to perform the action indicated by the user
 */
function update(key, leaveId, employeeId) {
	const params = "?action=" + key + "&leaveId=" + leaveId + "&employeeId=" + employeeId;
	let url = "ApproveRejectServlet" + params;
	fetch(url).then(res => res.json()).then(res => {
		let message = res;
		if (message && key === 'approve') {
			message = "Approved leave request";
		} else if (message && key === 'reject') {
			message = "Rejected leave Request";
		} else {
			message = "Failed to perform selected action";
			document.querySelector("#message").setAttribute("style", "color:red");
		}
		document.querySelector("#message").innerHTML = message;
		getRequests('waiting for approval');
	}).catch(err=>{
		console.log(err);
	});
}