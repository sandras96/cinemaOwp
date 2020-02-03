$(document).ready(function(e){
	
	var idScr = getUrlParameter('id');
	
	var div2 = $('#div2');
	getScreening();
	
	function addScreening(s){
		
		var title = $('<div class="col-md-8"><h2 class="movie-title"><a href="/Cinema/singleMovie.html?id='+s.movie.id+'"</a>'+s.movie.title+'</h2>');
		var date = $('<div class="movie-summary"><p>'+s.datetime+' </p></div>');
		var data = $('<ul class="movie-meta">'
						+'<li><strong>Directors:</strong>'+s.screentype.name+'</li>'
						+'<li><strong>Actors:</strong>'+s.auditorium.name+'</li>'
						+'<li><strong>Genre:</strong> '+s.ticketPrice+'</li>');
			  
	
		div2.append(title);
		title.append(date);
		date.append(data);
		
	
	}
	
	
	function getScreening(){
		var params = $.param({
			id : idScr,
		}) ;
		console.log(params);
		$.ajax({
			url: 'ScreeningServlet?' + params,
			method: 'GET',
			dataType: 'json',
			success: function(response){
				if(response.status == "success"){
					
					addScreening(response.screening);
				}else{
					alert.message;
				}
			},
			error: function(request, message, error){
				alert(error);
			}
		});
		
	};
	
	

	
	
	
	
	
});