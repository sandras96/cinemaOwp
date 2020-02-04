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
		var dateTime = $('<td>'+formatDate(new Date(s.datetime))+'</td>');
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
	

	var dateTo;
	var dateFrom;
	$("#dateFrom").on("change", function(e){
		dateFrom = new Date($(this).val()).getTime();
		
		document.getElementById("dateTo").min = dateFrom;
		
	//	getScreenings();
		
		console.log(dateFrom);
	});
	$("#dateTo").on("change", function(e){
		dateTo = new Date($(this).val()).getTime();
		getScreenings();
		console.log(dateTo);
	});
	
	
	
	function getScreenings(){
		var movieSearch = $("#movieSearch").val();
	//	var datetimeSearch = $("#datetimeSearch").val();
		var screentypeSearch = $("#screentypeSearch").find(":selected").val();
		var auditoriumSearch = $("#auditoriumSearch").find(":selected").val();
		var ticketPriceSearch = $("#ticketPriceSearch").val();
		var sortBy = $('#sortByScr').find(":selected").val();
		var ticketPrice1 = $("#ticketPrice1").val();
		var ticketPrice2 = $("#ticketPrice2").val();
		
		var params = $.param({
			movieSearch : movieSearch,
	//		datetimeSearch : datetimeSearch,
			screentypeSearch : screentypeSearch,
			auditoriumSearch : auditoriumSearch,
			ticketPriceSearch : ticketPriceSearch,
			dateFrom : dateFrom,
			dateTo : dateTo,
			ticketPrice1 : ticketPrice1,
			ticketPrice2 : ticketPrice2,
			sortBy : sortBy,
			
		});
		
		console.log("datumi su od :" + dateFrom + "do:" + dateTo);
		$.ajax({
			url:'ScreeningsServlet',
			method: 'GET',
			data : params,
			dataType: 'json',
			success: function(response){
				if(response.status == "success"){
					console.log("projekcije su: " + response.screenings);
					initScreenings(response.screenings)
					initMovies(response.movies);
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
	

	$("#movieSearch").change(function(e){
		getScreenings();
	});	
	$("#screentypeSearch").change(function(e){
		getScreenings();
	});	
	$("#auditoriumSearch").change(function(e){
		getScreenings();
	});	
	/*$("#ticketPriceSearch").change(function(e){
		getMovies();
	});*/	
	$("#sortByScr").change(function(e){
		getScreenings();
	});
	
	
//	$.ajax({
//		url:'ScreeningsServlet',
//		method: 'GET',
//		dataType: 'json',
//		success: function(response){
//			if(response.status == "success"){
//				console.log("dobila sam a" + response.movies)
//				initMovies(response.movies);
//			
//			}else{
//				alert(response.message);
//			}
//		},
//		error: function(request, message, error){
//			alert(error);
//		}
//	});
	function initMovies(movies){
		 var $select = $("#selMovies");
		 $select.find("option").remove(); 
		for (var i = 0; i < movies.length; i++) {
			 $("<option>").val(movies[i].id).text(movies[i].title).appendTo($select);
			
		}
	};

		
		$.ajax({
			url:'ScreentypeServlet',
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
				url : 'AuditoriumServlet',
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
	
	var datetime;
	$("#datetimePicker").on("change", function(e){
		datetime = new Date($(this).val()).getTime();
		
	});
	
	var saveScreening = $('#saveScreening2') ;
	saveScreening.click(function(e){
		e.preventDefault();
		console.log("dt je " + datetime);
		var movie = $('#selMovies').find(":selected").val();
		var screentype = $('#selectScreentype').find(":selected").val();
		var auditorium = $('#selectAuditorium').find(":selected").val();
		var ticketPrice = $('#priceTicket').val();
		
		var params = $.param({
			movieId : movie,
			screentypeId : screentype,
			auditoriumId : auditorium,
			datetime : datetime,
			ticketPrice : ticketPrice,
			action : "add",
			
		});
		console.log(params + "parametri za skrinnn" + "datummm je " + datetime)
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



