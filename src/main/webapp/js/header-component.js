function logout(){
	event.preventDefault();
	localStorage.removeItem("TOKEN");
	localStorage.removeItem("LOGGED_IN_USER");
	let url = "LogoutServlet";
	axios.get(url).then(res=>{
		toastr.success("Logged-out successfully");
		setTimeout(function(){
			window.location.href =  "login.jsp";
		},1000);
		
	})
}