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
	var addScr = $("#button2");
	addScr.hide();
	
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
				addScr.show();
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
		console.log("Mmmm")
		$.ajax({
			url:'ScreeningsServlet',
			method: 'GET',
			dataType: 'json',
			success: function(response){
				if(response.status == "success"){
					console.log("projekcije su: " + response.screenings);
					console.log("whaat")
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
	
	
	
	$("#selMovies").hide();
	$("#selectMovieBtn").click(function (e){
		console.log("select movie plssss")
		$("#selMovies").show();
		$.ajax({
			url:'ScreeningsServlet',
			method: 'GET',
			dataType: 'json',
			success: function(response){
				if(response.status == "success"){
					console.log("dobila sam filmoveee" + response.movies)
					initMovies(response.movies);
				
				}else{
					alert(response.message);
				}
			},
			error: function(request, message, error){
				alert(error);
			}
		});
		
		
		
	});
	function initMovies(movies){
		 var $select = $("#selMovies");
	//	 $select.find("option").remove(); 
		for (var i = 0; i < movies.length; i++) {
			 $("<option>").val(movies[i].id).text(movies[i].title).appendTo($select);
			
		}
	};
	$("#selectScreentype").hide();
	$("#selectScrtypeBtn").click(function (e){
		$("#selectScreentype").show();
		console.log("select skrin tajpsss plssss")
		
		$.ajax({
			url:'ScreeningsServlet',
			method: 'GET',
			dataType: 'json',
			success: function(response){
				if(response.status == "success"){
					console.log("dobila sam skrinsss" + response.screentypes)
					initScreentypes(response.screentypes);
				
				}else{
					alert(response.message);
				}
			},
			error: function(request, message, error){
				alert(error);
			}
		});
		
	});
	
	function initScreentypes(screentypes){
		 var $select = $("#selectScreentype");
	//	 $select.find("option").remove(); 
		for (var i = 0; i < screentypes.length; i++) {
			 $("<option>").val(screentypes[i].id).text(screentypes[i].name).appendTo($select);
		}
		$select.change(function(){
			console.log("changeee skrin tajps")
			
			var idScrtype = $(this).children("option:selected").val();
			
			var params = $.param({
				idScreenType : idScrtype,
			}) ; 
			console.log("params " + params)
			$.ajax({
				url : 'ScreeningsServlet',
				method: 'GET',
				data : params,
				dataType: 'json',
				success : function(response){
					if(response.status == "success"){
						console.log("sale suuu" + response.auditoriums)
						initAuditoriums(response.auditoriums);
					}else{
						alert(response.message)
					}
					
				},error: function(request, message, error){
					alert(error);
				}
			});
		});
		
	};
	
	function initAuditoriums(auditoriums){
		 var $select = $("#selectAuditorium");
		 $select.find("option").remove(); 
		for (var i = 0; i < auditoriums.length; i++) {
			 $("<option>").val(auditoriums[i].id).text(auditoriums[i].name).appendTo($select);
			
		}
	};
	
	
	var saveScreening = $('#saveScreening2') ;
	saveScreening.click(function(e){
		e.preventDefault();
		console.log("save skrinn")
		var movie = $('#selMovies').find(":selected").val();
		var screentype = $('#selectScreentype').find(":selected").val();
		var auditorium = $('#selectAuditorium').find(":selected").val();
		var date = $('#datetime').val();
		var ticketPrice = $('#priceTicket').val();
		
		var params = $.param({
			movieId : movie,
			screentypeId : screentype,
			auditoriumId : auditorium,
			date : date,
			ticketPrice : ticketPrice,
			action : "add",
			
		});
		console.log(params + "parametri za skrinnn")
		$.ajax({
			url : 'ScreeningsServlet',
			method : 'POST',
			data : params,
			dataType : 'json',
			success : function(response){
				if(response.status == "succcess"){
					location.reload();
					console.log("Nova projekcija je: " + response.screening)
				}else{
					alert(response.message);
				}
			},error: function(request, message, error){
				alert(error);
			}
			
		});
		
		$('#addScreening').modal('toggle');
		
	});
	
});



