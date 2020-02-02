$(document).ready(function(e){
	
	
	
	var username = getUrlParameter('username');
	
	var usrDiv = $('#usrContainer');

	var liUsers = $('#liUsers');
	liUsers.hide();
	var deleteUserBtn = $('#deleteUserBtn');
	deleteUserBtn.hide();
	
	var updateRole = $('#updateRole');
	updateRole.hide();
	
	function showHide(loggedInUser){
		if(loggedInUser.role == "ADMIN"){
			liUsers.show();
		}
		$('#goToProfile').button().click(function(){
			window.location.href = '/Cinema/user.html?username=' + loggedInUser.username;
		});
	}
	
	
	getUser();
	
	
	function initUser(u, loggedInUser){
		var username = $('<h4><b>Username: </b>'+u.username+'</h4> ');
		var registrationDate = $(' <p><b>Registration date: </b>'+u.datetime+'</p> ');
		var role = $('<p><b>Role: </b>'+u.role+'</p>');
		var pencil = $('<p><button data-toggle="modal" type="button" data-target="#editUser" class="glyphicon glyphicon-pencil"></button></p> ');

		usrDiv.append(username);
		usrDiv.append(registrationDate);
		usrDiv.append(role);
		usrDiv.append(pencil);
	
		
		if(loggedInUser.role=="ADMIN"){
			updateRole.show();
			usrDiv.append(pencil);
			
			if(loggedInUser.username != u.username){
				deleteUserBtn.show();
			}
			
		}
		
	
			
	
		
		
		
	};
	
	deleteUserBtn.click(function(e){
		e.preventDefault();
		var params = $.param({
			usr : username,
			action : "delete"
		});
		console.log(params);
		$.ajax({
			url: 'UserServlet?' + params,
			method: 'POST',
			dataType: 'json',
			success : function(response){
				if(response.status == "success"){
					alert(response.status)
					window.location.replace('/Cinema/users.html')
				}else{
					alert(response.message);
				}
			},
			error: function(request, message, error){
				alert(error);
			}
		});
	});
	
	function getUser(){
		var params = $.param({
			usr : username,
		});
		console.log(params)
		$.ajax({
			url: 'UserServlet?' + params,
			method: 'GET',
			dataType: 'json',
			success : function(response){
				if(response.status == "success"){
					console.log("konzola j "+ response.user.username)
					initUser(response.user, response.loggedInUser);
					showHide(response.loggedInUser);
				}else{
					alert(response.message)
					window.location.replace("index.html");
				}
			},
			error: function(request, message, error){
				alert(error);
			}	
			
		});
		
	};
	
	var saveUser = $('#saveUser');
	
	saveUser.click(function(e){
		e.preventDefault();
		var newPass = $('#newPass').val();
		var select = $('#selectRole').find(":selected").text();
		var params = $.param({
			newPass : newPass,
			select : select,
			usr: username,
			action : "update",
		});
		console.log(params);
		$.ajax({
			url: 'UserServlet',
			method: 'POST',
			data : params,
			dataType : 'json',
			success : function(response){
				if(response.status == "success"){
					location.reload();
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