$(document).ready(function(e){
	
	
	
	var username = getUrlParameter('username');
	
	var usrDiv = $('#usrContainer');

	var ticketsDiv = $("#ticketsDiv");
	
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
	getTickets();
	
	function initUser(u, loggedInUser){
		var username = $('<h4><b>Username: </b>'+u.username+'</h4> ');
		var registrationDate = $(' <p><b>Registration date: </b>'+formatDate(new Date(u.datetime))+'</p> ');
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
	
	
	function getTickets(){
		var params = $.param({
			username : username
		});
		$.ajax({
			url : 'TicketsForUserServlet',
			method : 'GET',
			dataType: 'json',
			data: params,
			success : function(response){
				if(response.status == "success"){
					initTickets(response.tickets);
				}else{
					alert(response.message);
				}
			},
			error: function(request, message, error){
				alert(error);
			}	
			
			
			
		});
		
	}
	
	function initTickets(tickets){
		ticketsDiv.empty();
		for (var i = 0; i < tickets.length; i++) {
			appendTickets(tickets[i]);
		}
	}
	function appendTickets(t){
		var tableRow= $('<tr></tr>');
		var title = $('<td><a href="/Cinema/singleMovie.html?id='+t.screening.movie.id+'"</a>'+t.screening.movie.title+'</div>');
		var datetimeScr = $('<td>'+formatDate(new Date(t.screening.datetime))+'</td>');
		var screenType = $('<td> '+t.screening.screentype.name+'</td>');
		var auditorium = $('<td>'+t.screening.auditorium.name+'</td>');
		var ticketPrice = $('<td>'+t.screening.ticketPrice+'</td>');
		var datetimeTicket = $('<td> '+formatDate(new Date(t.datetime))+'</td>');
		var deleteTicket = $('<td><button id="deleteTicket' + t.id +'" value="' + t.id + '" type="button">delete ticket </button></td>');

		tableRow.append(title);
		tableRow.append(datetimeScr);
		tableRow.append(screenType);
		tableRow.append(auditorium);
		tableRow.append(ticketPrice);
		tableRow.append(datetimeTicket);
		tableRow.append(deleteTicket);
	
		ticketsDiv.append(tableRow);
		var deleteTicketId = "#deleteTicket" + t.id;
		$(deleteTicketId).click(function(e){
			var ticketId = $(this).val();
			var params = $.param({
				ticketId : ticketId,
			});
			$.ajax({
				url: 'TicketsForUserServlet',
				method: 'POST',
				dataType: 'json',
				data : params,
				success: function(response){
					if(response.status =="success"){
						alert(response.message);
					}else{
						alert(response.message);
					}
				},
				error: function(request, message, error){
					alert(error);
				}	
			
			});
			
			
		});
		
	}
	
	
});