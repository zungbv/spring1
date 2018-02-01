var usernameGlobal;
function fillForm(index){
	var firstName=$("#firstName"+index).text();
	var lastName=$("#lastName"+index).text();
	var address=$("#address"+index).text();
	var phone=$("#phone"+index).text();
	var email=$("#email"+index).text();
	var image=$("#image"+index).val();
	var username=$("#username"+index).val();
	usernameGlobal=username;
	$("#firstName").val(firstName);
	$("#lastName").val(lastName);
	$("#address").val(address);
	$("#phone").val(phone);
	$("#email").val(email);
	$(".username").val(username);
	d = new Date();
	$(".userImage").attr("src","/userImage/"+image+"?"+d.getTime());
}
function deleteUser(index){
	var username=$("#username"+index).val();
	if (confirm("Delete "+username+" ?")) {
		$.get("/admin/deleteUser/"+username,function(data){
			var dt;
			try {
				dt = JSON.parse(data);
				if (dt) {
					location.reload();
				} else {
					alert("Delete fail!")
				}
			} catch (e) {
				window.location = "login";
			}
		});
    }
}



$(document).ready(function() {
	$("#updateUserEmailForm").submit(function(event) {
		event.preventDefault();
		$.post("/admin/updateUserEmail", {
			confirmPassword : $("#confirmPassword").val(),
			newEmail : $("#newEmail").val(),
			username: usernameGlobal
		}, function(data) {
			var dt;
			try {
				dt = JSON.parse(data);
				if (dt) {
					location.reload();
				} else {
					var mes = "Password incorrect"
					$("#updateUserEmailMessage").html(mes);
				}
			} catch (e) {
				window.location = "login";
			}

		});
	})
});