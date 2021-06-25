/**
 * This method is used to all leave requests
 */
function getAllRequests(arg) {
	let url = "RequestStatusServlet";
	fetch(url).then(res => res.json()).then(res => {
		let requests = res;
		let content = "";
		let serial = 1;
		for (let request of requests) {
			if (arg === null || request.status === arg) {
				content +=
					"<tr><td>" + serial + "</td><td>"
					+ request.employee.employeeId + "</td><td>"
					+ request.employee.name + "</td><td>"
					+ request.fromDate + "</td><td>"
					+ request.toDate + "</td><td>"
					+ request.duration + "</td><td>"
					+ request.status + "</td><td>"
					+ request.appliedTime + "</td>";
				serial++;
			}
			if (arg === 'waiting for approval' && arg === request.status) {
				content += "<td><button class=\"btn btn-danger\" onclick=\"cancel(" + request.leaveId + ")\"+ > Cancel</button ></td></tr>"
			} else {
				content += "</tr>";
			}
		}

		document.querySelector("#table-body").innerHTML = content;
	})
}
getAllRequests(null);


/**
 * This method is used to cancel a leave request with leave id as reference
 */
function cancel(leaveId) {
	if (window.confirm("Do you want to cancel this request?")) {
		let url = "CancelRequestServlet?leaveId=" + leaveId;
		axios.get(url).then(res => {
			let data = res.data;
			if (data) {
				toastr.success("Cancelled successfully");
			} else {
				toastr.error("Failed to cancel");
			}
			getAllRequests(null);
		}).catch(err => {
			console.log(err.response.data.errorMessage);
		});
	}
}