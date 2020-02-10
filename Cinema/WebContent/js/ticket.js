$(document).ready(function (e){
	

	var idMovie = getUrlParameter('id');
	
	var screenings1Div = $("#screenings1Div");
	var divShowScreen = $("#divShowScreen");
	
	getScreenings();


	function initScreenings(screenings){
		screenings1Div.empty();
		for (var i = 0; i < screenings.length; i++) {
			appendScreening(screenings[i]);
		}
	};
	function appendScreening(s){
		var tableRow = $('<tr>');
		var dateTime = $('<td><a href="/Cinema/singleScreen.html?id='+s.id+'"</a>'+formatDate(new Date(s.datetime))+'</td>');
		var typeScreen = $('<td>'+s.screentype.name+'</td>');
		var auditorium = $('<td>'+s.auditorium.name+'</td>');
		var ticketPrice = $('<td>'+s.ticketPrice+'</td></tr>');
		var byTicket = $('<td><button type="button" id="byTicketBtn'+s.id+'" class="glyphicon glyphicon-piggy-bank byTicketBtn" value="'+s.id+'"></td></tr>');
	//	screenings1Div.empty();
		tableRow.append(dateTime);
		tableRow.append(typeScreen);
		tableRow.append(auditorium);
		tableRow.append(ticketPrice);
		tableRow.append(byTicket);
		screenings1Div.append(tableRow);
		
		var btnId = "#byTicketBtn" + s.id;
		var byTicketBtn = $(btnId);
		byTicketBtn.click(function(e){
			var screenId = $(this).attr("value");  
			var table = $("#myTable");
			table.hide();
			var params = $.param({
				id : screenId,
			});
			$.ajax({
				url : 'ScreeningServlet',
				method : 'GET',
				data: params,
				dataType : 'json',
				success : function(response){
					if(response.status =="success"){
						showScreening(response.screening);
						initSeats(response.seats);
					}else{
						alert(response.message)
					}
				},
				errorr : function(request, response, error){
					
				}
			});
			function initSeats(seats){
				 var $select = $("#selSeats");
				 $select.find("option").remove(); 
				for (var i = 0; i < seats.length; i++) {
					 $("<option>").val(seats[i].id).text(seats[i].seatNO).appendTo($select);
					
				}
		/*		$select.change(function(){
					console.log("changeee seatsss")
					var seat = $(this).val().join(",");
					
					var params = $.param({
						seats: seat,
					});
					
					console.log(params)
							
					$.ajax({
						url : 'TicketServlet',
						method: 'POST',
						data : params,
						dataType: 'json',
						success : function(response){
							if(response.status == "success"){
								console.log(response)
							}else{
								alert(response.message)
							}
							
						},error: function(request, message, error){
							alert(error);
						}
					});
				});*/
				
			};
			
			function showScreening(s){
				
				var div= $('<div class="form-group">');
				var movieTitle = $('<label for="" id="">Movie title:<a href="/Cinema/singleMovie.html?id='+s.movie.id+'"> '+s.movie.title+'</a></label></div>')
				var datetime = $('<div class="form-group"><label for="password" id="">Datetime: </label>'+s.datetime+'</div>');
				var screentype = $('<div class="form-group"><label for="password" id="">Screeningtype: </label>'+s.screentype.name+'</div>');
				var auditorium = $('<div class="form-group"><label for="password">Auditorium: </label>'+s.auditorium.name+'</div>');
				var ticketPrice = $('<div class="form-group"><label for="password" id="">TicketPrice: </label>'+s.ticketPrice+'</div></div>');
				var ticket = $('<div class="form-group" id="chooseSeatDiv"><label for="">Choose seat:</label><select multiple class="form-control" id="selSeats" style= "width:60px">'+
						/*'<option value=""></option>'+
						'<option value=""></option>'+
						'<option value=""></option>'+
						'<option value=""></option>'+
						'<option value=""></option>'+*/
						'</select></div>');
				var confirmSeats  = $('<td><button type="button" id="confirmSeats" class="glyphicon glyphicon-ok"></button></td>')
				
				div.append(movieTitle);
				movieTitle.append(datetime);
				datetime.append(screentype);
				screentype.append(auditorium);
				auditorium.append(ticketPrice);
				ticketPrice.append(ticket);
				ticket.append(confirmSeats);
				divShowScreen.append(div);
				
				 $("#confirmSeats").click(function(e){
					$("#chooseSeatDiv").hide();
					confirmSeats.hide()
					var cseats = $('#selSeats option:selected').toArray().map(item => item.text).join();
					var seatAmount = $("#selSeats").val().length;
					var ticketPriceSum = s.ticketPrice * seatAmount;
					var amount = $('<td><label>Chosen seats: ' + cseats + '</label><br/><label>Full amount: ' + ticketPriceSum + ' </label></td><br/><br/>');
					var submitTicket = $('<td><button type="button" id="submitTicket">Submit</button></td>');
					
					ticketPrice.append(amount);
					amount.append(submitTicket);
					
					
					
					var auditoriumId = s.auditorium.id;
					var screeningId = s.id;
					
					$("#submitTicket").click(function (e){
						var seat = $("#selSeats").val().join(",");
						var params = $.param({
							seats: seat,
							auditoriumId : auditoriumId,
							screeningId : screeningId,
							
						});
						
						$.ajax({
							url : 'TicketServlet',
							method: 'POST',
							data : params,
							dataType: 'json',
							success : function(response){
								if(response.status == "success"){
									alert(response.message)
									
								}else{
									alert(response.message)
								}
								
							},error: function(request, message, error){
								alert(error);
							}
						});
					});
					
				});
				
				
			}
		});
		
		
	};

	function getScreenings(){
		console.log("projekcije za film")
		var params = $.param({
			movieId : idMovie,
		}) ;
		console.log(params)
		$.ajax({
			url: 'TicketServlet',
			method : 'GET',
			data : params,
			dataType : 'json',
			success : function(response){
				console.log(response)
				if(response.status == "success"){
					initScreenings(response.screeningsForMovie);
				}else{
					alert(response.message)
				}
			},
			error: function(request, message, error){
				alert(error);
			}
			
			
			
		});
	
	
	}
	
	
	
	
	
	
	
	
	
	
	
});