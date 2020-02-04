$(document).ready(function(e){
	
	$('#confirm').submit(function(e){
		e.preventDefault();
		
		var usernameInput = $('#usr').val();
		var passwordInput = $('#pwd').val();
		var passwordRepeatedInput = $('#rpwd').val();
		
		var params = $.param({
			usernameReg : usernameInput,
			passwordReg : passwordInput,
			passwordRep : passwordRepeatedInput,
			
		});
		console.log(params);
		$.ajax({
			url: 'RegistrationServlet',
			data : params,
			method: 'POST',
			dataType: 'json',
			success : function(response){
				if(response.status == "success"){
					console.log(response.loggedInUser.username);
					window.location.replace('/Cinema/user.html?username='+ response.loggedInUser.username)
				}else{
					alert(response.message);
				}
			},
			error: function(request, message, error){
				alert(error);
			}
			
		});
	});
	
	
	
	

});


	

	
