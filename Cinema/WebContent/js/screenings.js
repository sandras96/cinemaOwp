$(document).ready(function(e){
	
	var screeningsDiv = $('#screeningsDiv');
	getScreenings();
	
	
	var liUsers = $('#liUsers');
	liUsers.hide();
	var liMyProfile = $('#liMyProfile');
	liMyProfile.hide();
	var logoutButton = $('#logoutButton');
	logoutButton.hide();	
	var liLogin = $('#liLogin');
	var liSignUp = $('#liSignUp');
	
	function showHide(loggedInUser){
		if(loggedInUser!=null){
			
			$('#goToProfile').button().click(function(){
				window.location.href = '/Cinema/user.html?username=' + loggedInUser.username;
			});
			liMyProfile.show();
			logoutButton.show();
			liLogin.hide();
			liSignUp.hide();
			
			if(loggedInUser.role == "ADMIN"){
				liUsers.show();
			}
		}
	}
	
	function initScreenings(screenings){
		screeningsDiv.empty();
		for (var i = 0; i < screenings.length; i++) {
			appendScreening(screenings[i]);
		}
	};
	
	function appendScreening(s){
		var tableRow = $('<tr>');
		var movies = $('<td><a href="/Cinema/singleScreen.html?id='+s.id+'"</a>'+s.movie.title+'</div>');
		var dateTime = $('<td>'+s.datetime+'</td>');
		var typeScreen = $('<td>'+s.screentype.name+'</td>');
		var auditorium = $('<td>'+s.auditorium.name+'</td>');
		var ticketPrice = $('<td>'+s.ticketPrice+'</td></tr>');
		
		tableRow.append(movies);
		tableRow.append(dateTime);
		tableRow.append(typeScreen);
		tableRow.append(auditorium);
		tableRow.append(ticketPrice);
		screeningsDiv.append(tableRow);
		
		
	}
	
	
	function getScreenings(){
		$.ajax({
			url:'ScreeningsServlet',
			method: 'GET',
			dataType: 'json',
			success: function(response){
				if(response.status == "success"){
					console.log("projekcije su: " + response.screenings);
					initScreenings(response.screenings)
					console.log("loggedInUser je"+ response.loggedInUser);
					showHide(response.loggedInUser);
				}else{
					alert(response.message);
				}
			},
			error: function(request, message, error){
				alert(error);
			}
		});
		
		
	}
	
	
	
});