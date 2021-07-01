<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<form id = "form" enctype="multipart/form-data" onsubmit = "upload()">
	<input type="file" name="file" id="file" accept=".xlsx,.csv"/>
	<input type="submit" name="submit" id="sumbit"/>
</form>
<script type="text/javascript">
function upload(){
	event.preventDefault();
	let form = document.getElementById("form");
	let formData = new FormData(form);
	$.ajax( {
	      url: "import/csv",
	      type: 'POST',
	      data: formData,
	      processData: false,
	      contentType: false
	    } ).done(function(d) {
	          toastr.success(d);
	    });
// 	let form =document.querySelector("#form");
// 	let formData = new FormData();
// 	formData.append("file",file[0]);
// 	let url = "import/csv";
// 	let obj = {};
// 	for(key of formData.keys()){
// 		obj[key] = formData.get(key);
// 	}
// 	console.log(obj);
// 	axios.post(url).then(res=>{
// 		console.log(res.data);
// 	}).catch(err=>{
// 		console.log(err);
// 	});
}

</script>
</body>
</html>