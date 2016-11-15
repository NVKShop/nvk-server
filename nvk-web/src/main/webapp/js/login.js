$(function() {
	
	var usernameField = $("#userName");
	if (usernameField.value === "") {
		usernameField.focus();
	} else {
		$("#password").focus();
	}
});
