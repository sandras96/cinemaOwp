$(document).ready(function(e){
	
	var loggedInUser = null;
	var usersDiv = $('#usersDiv');
	
	$('#goToProfile').button().click(function(){
		window.location.href = '/Cinema/user.html?username=' + loggedInUser.username;
	});
	getUsers();
	
	function initUsers(users){
		usersDiv.empty();
		for (var i = 0; i < users.length; i++) {
			appendUser(users[i]);
		}
	};
	function appendUser(user){
		var tableRow= $('<tr></tr>');
		var name = $('<td><a href="/Cinema/user.html?username='+user.username+'"</a>'+user.username+'</div>');
		var datetime = $('<td>'+formatDate(new Date(user.datetime))+'</td>');
		var role = $('<td>'+user.role+'</td>');

		tableRow.append(name);
		tableRow.append(datetime);
		tableRow.append(role);
	
		usersDiv.append(tableRow);
		
	}
	
	function getUsers(){
		var usernameSearch = $("#usernameSearch").val();
		var roleSearch = $('#roleSearch').find(":selected").val();
		var sortBy = $('#sortBy').find(":selected").val();
		
		var params = $.param({
			usernameSearch :  usernameSearch,
			roleSearch : roleSearch,
			sortBy : sortBy,
		});
		console.log("paramssss" + params); 
		$.ajax({
			url: 'UsersServlet',
			method: 'GET',
			data : params,
			dataType: 'json',
			success: function(response){
				if(response.status == "success"){
					initUsers(response.users);
					console.log("haaaaj" + response.users);
					loggedInUser = response.loggedInUser;
				}else{
					console.log(response)
					alert(response.message);
					window.location.replace("index.html");
				}
		
			},
			error: function(request, message, error){
				alert(error);
			}
		});
	};
	
	$("#usernameSearch").change(function(e){
		getUsers();
	});
	$("#roleSearch").change(function(e){
		getUsers();
	});
	$("#sortBy").change(function(e){
		getUsers();
	});

	/*var submitSearch = $('#submitSearch');
	
	submitSearch.click(function(e){
		e.preventDefault();
		var select = $('#searchSelect').find(":selected").text();
		var input = $('#inputSearch').val();
		var params = $.param({
			inputSearch : input,
			select : select,
			
		})
		console.log(params)
		$.ajax({
			url: 'UsersServlet?' + params,
			method: 'GET',
			dataType: 'json',
			success : function(response){
				if(response.status = "success"){
					initUsers(response.users);
					
				}else{
					console.log("blaaa")
					alert(response.message);
				}
			},
			error: function(request, message, error){
				alert(error);
			}
			
		});
	});*/
	
	
/*	$('#usernameAsc_btn').click(function(e){
		e.preventDefault();
		var input = $('#inputSearch').val();
		var usernameSearch = $("#usernameSearch").val();
		var roleSearch = $('#roleSearch').find(":selected").val();
		var params = $.param({
			usernameSearch :  usernameSearch,
			roleSearch : roleSearch,
			direction: "ASC",
			orderBy: "username",
			inputSearch : input,
		});
		console.log(params);
		$.ajax({
			url: 'UsersServlet?' + params,
			method: 'GET',
			dataType: 'json',
			success: function(response){
			//	getUsers();
			},
			error: function(request, message, error){
				alert(error)
			}
			
		});
	});*/
	
	/*$('#usernameDesc_btn').click(function(e){
		e.preventDefault();
		var input = $('#inputSearch').val();
		var params = $.param({
			direction: "DESC",
			orderBy: "username",
			inputSearch : input,
		});
		console.log(params);
		$.ajax({
			url: 'UsersServlet?' + params,
			method: 'GET',
			dataType: 'json',
			success: function(response){
				initUsers(response.users);
			},
			error: function(request, message, error){
				alert(error)
			}
			
		});
	});
	
	$('#roleAsc_btn').click(function(e){
		e.preventDefault();
		var input = $('#inputSearch').val();
		var params = $.param({
			direction: "ASC",
			orderBy: "role",
			inputSearch : input,
		});
		console.log(params);
		$.ajax({
			url: 'UsersServlet?' + params,
			method: 'GET',
			dataType: 'json',
			success: function(response){
				initUsers(response.users);
			},
			error: function(request, message, error){
				alert(error)
			}
			
		});
	});
	
	$('#roleDesc_btn').click(function(e){
		e.preventDefault();
		var input = $('#inputSearch').val();
		var params = $.param({
			direction: "DESC",
			orderBy: "role",
			inputSearch : input,
		});
		console.log(params);
		$.ajax({
			url: 'UsersServlet?' + params,
			method: 'GET',
			dataType: 'json',
			success: function(response){
				initUsers(response.users);
			},
			error: function(request, message, error){
				alert(error)
			}
			
		});
	});*/
	
	
});