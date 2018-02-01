$(document).ready(function() {
	$("#updateUserEmailForm").submit(function(event) {
		event.preventDefault();
		$.post("user/updateUserEmail", {
			confirmPassword : $("#confirmPassword").val(),
			newEmail : $("#newEmail").val()
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

		/*
		 * var obj={}; obj["newEmail"]=$("#newEmail").val();
		 * obj["confirmPassword"]=$("#confirmPassword").val(); $.ajax({ url:
		 * "user/updateUserEmail", data: JSON.stringify(obj), type: "POST",
		 * contentType: "application/json; charset=utf-8", dataType: 'json',
		 * success: function(data) { var obj=JSON.parse(data);
		 * if(obj.message=="true"){ location.reload(); } else{ var mes="Password
		 * incorrect" $("#updateUserEmailMessage").html(mes);
		 *  } } });
		 */

	})
});