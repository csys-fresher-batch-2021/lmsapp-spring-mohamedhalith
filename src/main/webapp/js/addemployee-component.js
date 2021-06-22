/**
	* This function sets the value of the joined date field also add boundary condition 
	* to the joined date field. Limits maximum to present day and minimum to 30 days
	* from present day. In other words, admin cannot add an employee joined before a month
	*/
function setDate() {
	let joinedDate = document.getElementById("joinedDate");
	let today = new Date();
	let date = today.toJSON().substr(0, 10);
	joinedDate.value = date;
	joinedDate.max = date;
	let pastDate = new Date();
	pastDate.setDate(pastDate.getDate() - 30);
	joinedDate.min = pastDate.toJSON().substr(0, 10);
}
// Function call
setDate();
/**
* This function generates default username and password to the employee and sets it to 
* the fields username and password fields.
*/
function setUsername() {
	let name = document.getElementById("name").value.toLocaleLowerCase();
	let firstFour = "";
	if (name.length >= 4) {
		firstFour = name.substr(0, 4);
	} else {
		let length = 4 - name.length;
		while (length) {
			firstFour = firstFour + "a";
			length = length - 1;
		}
		firstFour = name + firstFour;
	}
	let employeeId = document.getElementById("employeeId").value.toLocaleLowerCase();
	let secondFour = "";
	if (employeeId.length >= 4) {
		secondFour = employeeId.substr(0, 4);
	} else {
		let length = 4 - employeeId.length;
		while (length) {
			secondFour = secondFour + length;
			length = length - 1;
		}
		secondFour = employeeId + secondFour;
	}
	let username = firstFour + secondFour;
	document.querySelector("#username").value = username;
	let password = secondFour + firstFour;
	document.querySelector("#password").value = password;
}

function add() {
	event.preventDefault();
	let url = "AddEmployeeServlet";
	let name = document.querySelector("#name").value;
	let employeeId = document.querySelector("#employeeId").value;
	let mobileNumber = document.querySelector("#mobileNumber").value;
	let email = document.querySelector("#email").value;
	let username = document.querySelector("#username").value;
	let password = document.querySelector("#password").value;
	let joinedDate = document.querySelector("#joinedDate").value;

	let employee = {
		"name": name,
		"employeeId": employeeId,
		"mobileNumber": mobileNumber,
		"email": email,
		"username": username,
		"password": password,
		"joinedDate": joinedDate
	}
	console.log(employee);
	axios.post(url, employee).then(res => {
		let data = res.data;
		if (data) {
			alert("Employee Added Successfully");
			window.location.href = "listemployees.jsp";	
		}
	}).catch(err => {
		console.log(err.response.data);
	});
}