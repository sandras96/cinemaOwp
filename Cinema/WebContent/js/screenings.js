$(document).ready(function(e){
	
	var screeningsDiv = $('#screeningsDiv');
	getScreenings();
	document.getElementById("selectScreentype").disabled = true;
	document.getElementById("selectAuditorium").disabled = true;
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
	

	var dateFrom;
	var dateTo;
	$("#dateFrom").on("change", function(e){
		dateFrom = new Date($(this).val()).getTime();
		
		 var date = new Date(dateFrom); 
		 var x = test3(date);
		 document.getElementById("dateTo").min = x;
		 console.log(x);
		 
		/* var y = test4(date);
		 console.log("y je " + y);*/
		
		
	//	 var x = date.toUTCString();
	//	 var x = date.toString();
	//	 console.log("proba 555"+ x);
		
	/*	var depTime = new Date(1581724800000).toISOString();
		 console.log(depTime.split(":")[0]);
		 console.log(depTime.split(":")[1]);
		 console.log(depTime.split(":")[0]);
		 console.log("datum je " + (depTime.split(":")[0]) + 1 + ":" + depTime.split(":")[1]);
		 var a = depTime.split(":")[0] + ":" + depTime.split(":")[1];
		 console.log("umrecu " + depTime);*/
		 
//
		
	//	getScreenings();
		
		console.log(dateFrom);
	});
	$("#dateTo").on("change", function(e){
		dateTo = new Date($(this).val()).getTime();
		if(dateTo < dateFrom){
			document.getElementById("demo").innerHTML = "Wrong date";
			screeningsDiv.empty();
		}else{
			document.getElementById("demo").innerHTML = "";
			getScreenings();
		}
	});
	
	
	
	function getScreenings(){
		var movieSearch = $("#movieSearch").val();
		var screentypeSearch = $("#screentypeSearch").find(":selected").val();
		var auditoriumSearch = $("#auditoriumSearch").find(":selected").val();
		var ticketPriceSearch = $("#ticketPriceSearch").val();
		var sortBy = $('#sortByScr').find(":selected").val();
		var ticketPrice1 = $("#ticketPrice1").val();
		var ticketPrice2 = $("#ticketPrice2").val();
		
		var params = $.param({
			movieSearch : movieSearch,
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
			
			var movie = $('#selMovies').find(":selected").val();
			var idScrtype = $(this).children("option:selected").val();
			var datetime = new Date($("#datetimePicker").val()).getTime();
			
			var params = $.param({
				idScreenType : idScrtype,
				datetime : datetime,
				movieId : movie,
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
	var date = test3(new Date().getTime());
	document.getElementById("datetimePicker").min = date;
	$("#datetimePicker").on("change", function(e){
		datetime = new Date($(this).val()).getTime();
		console.log(datetime);
		var newDate = new Date().getTime();
		if(datetime < newDate){
			document.getElementById("demo2").innerHTML = "Wrong date";
			document.getElementById("saveScreening2").disabled = true;
			document.getElementById("selectScreentype").disabled = true;
			document.getElementById("selectAuditorium").disabled = true;
		}else{
			document.getElementById("demo2").innerHTML = "";
			document.getElementById("saveScreening2").disabled = false;
			document.getElementById("selectScreentype").disabled = false;
			document.getElementById("selectAuditorium").disabled = false;
			
		}
		
	});
	
	var saveScreening = $('#saveScreening2') ;
	saveScreening.click(function(e){
		
		e.preventDefault();
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
					alert(response.message);
					console.log("Nova projekcija je: " + response.screening)
				}else{
					alert(response.message);
				}
			},error: function(request, message, error){
				alert(error);
			}
			
		});
		
		$('#addScreening').modal('toggle');
		location.reload();
		
	});
	
	
	

	
});



