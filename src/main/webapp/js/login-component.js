/**
 * This method is used to toggle type of the input 
 */
function toggle() {
	let eyefull = document.getElementById('eyefull');
	let password = document.getElementById("password");
	/**
	* Function to show password (toggle between the type of password)
	*/
	eyefull.addEventListener('click', function() {
		let type = password.getAttribute('type') === 'password' ? 'text' : 'password';
		password.setAttribute('type', type);
		let className = eyefull.getAttribute('class') === 'fas fa-eye' ? 'fas fa-eye-slash' : 'fas fa-eye';
		eyefull.setAttribute('class', className);
	});
}
toggle();

function login(){
	event.preventDefault();
	let username = document.querySelector("#username").value;
	let password = document.querySelector("#password").value;
	let role = document.querySelector('input[name="role"]:checked').value;
	let url = "LoginServlet";
	let user = {
		"username" : username,
		"password" : password,
		"role" : role
	}
	axios.post(url,user).then(res=>{
		let data  = res.data;
		console.log(res);
		if(data != null){
			alert("Logged in successfully");
			localStorage.setItem("LOGGED_IN_USER",user);
			window.location.href="index.jsp";
		}
	}).catch(err=>{
		console.log(err.response.data);
		alert("invalid credentials");
	});
}