$(document).ready(function(e){

	var idMovie = getUrlParameter('id');
	console.log("filmovi")
	
	var moviesDiv = $('#moviesDiv');
	var liUsers = $('#liUsers');
	liUsers.hide();
	var liMyProfile = $('#liMyProfile');
	liMyProfile.hide();
	var logoutButton = $('#logoutButton');
	logoutButton.hide();	
	var liLogin = $('#liLogin');
	var liSignUp = $('#liSignUp');
	var createMovie = $('#button2');
	createMovie.hide();
	
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
				createMovie.show();
			}
			
		}
	}
	getMovies();
	
	function initMovies(movies){
		moviesDiv.empty();
		for (var i = 0; i < movies.length; i++) {
			appendMovie(movies[i]);
		}
	};
	 
	
/*function appendMovie(movie){
		var col1 = $('<div class="col-sm-3">');
	//	var col2 = $(' <div class="card h-100">');
		var img = $('<img class="card-img-top" src="img/singlMovie.jpg" alt="">');
		var col3 = $('<div class="card-body">');
		var title = $('<h4 class="card-title">'+movie.title+'</a></h4>')
		var data = $('<p class="card-text"><b>genre:</b> '+movie.genre+";</br>  "+'<b>duration:</b> '+movie.duration+"; "+'<b>distributor:</b> '+movie.distributor+";</br> "+ '<b>country:</b> '+movie.country+"; "+'<b>year:</b> '+movie.year+"; "+' </p></div>');
		var footer = $('<div class="card-footer">');
		var fOM = $('<a href="/Cinema/singleMovie.html?id='+movie.id+'" class="btn btn-default">Find Out More!</a></div>');
	//	col1.append(col2);
		col1.append(img);
		col1.append(col3);
		col1.append(title);
		col1.append(data);
		movieCard.append(col1);
		data.append(footer);
		footer.append(fOM);
	}*/

function appendMovie(movie){
	var tableRow = $('<tr>');
	var title = $('<td><a href="/Cinema/singleMovie.html?id='+movie.id+'"</a>'+movie.title+'</td>');
	var genre = $('<td>'+movie.genre+'</td>');
	var duration = $('<td>'+movie.duration+'</td>');
	var distributor = $('<td>'+movie.distributor+'</td>');
	var country = $('<td>'+movie.country+'</td>');
	var year = $('<td>'+movie.year+'</td></tr>');
	
	tableRow.append(title);
	tableRow.append(genre);
	tableRow.append(duration);
	tableRow.append(distributor);
	tableRow.append(country);
	tableRow.append(year);
	moviesDiv.append(tableRow);
	
}
	
	function getMovies(){
		var titleSearch = $("#titleSearch").val();
		var genreSearch = $("#genreSearch").val();
		var durationSearch = $("#durationSearch").val();
		var distributorSearch = $("#distributorSearch").val();
		var countrySearch = $("#countrySearch").val();
		var yearSearch = $("#yearSearch").val();
		
		var params = $.param({
			titleSearch : titleSearch,
			genreSearch : genreSearch,
			durationSearch : durationSearch,
			distributorSearch : distributorSearch,
			countrySearch : countrySearch,
			yearSearch : yearSearch,
			
		});
		console.log(params)
		$.ajax({
			url: 'Movies2Servlet?' + params,
			method: 'GET',
			dataType: 'json',
			success: function(response){
				if(response.status == "success"){
					console.log("filmovi su" + response.movies)
					showHide(response.loggedInUser);
					initMovies(response.movies);
				}else{
					console.log(response)
					alert(response.message)
				}
			},
			error: function(request, message, error){
				alert(error);
			}
		});
	};
	
	$("#titleSearch").change(function(e){
		getMovies();
	});	
	$("#genreSearch").change(function(e){
		getMovies();
	});	
	$("#durationSearch").change(function(e){
		getMovies();
	});		
	$("#distributorSearch").change(function(e){
		getMovies();
	});	
	$("#countrySearch").change(function(e){
		getMovies();
	});	
	$("#yearSearch").change(function(e){
		getMovies();
	});	
	
	var saveMovie = $('#saveMovie');
	
	saveMovie.click(function(e){
		e.preventDefault();
		var titleInput = $('#title').val();
		var directorsInput = $("#directors").val();
		var actorsInput = $('#actors').val();
		var genreInput = $('#genre').val();
		var durationInput = $('#duration').val();
		var distributorInput = $("#distributor").val();
		var countryInput = $("#country").val();
		var yearInput = $("#year").val();
		var descriptionInput = $("#description").val();
		
		var params = $.param({
			titleInput : titleInput,
			directorsInput : directorsInput,
			actorsInput : actorsInput,
			genreInput : genreInput,
			durationInput : durationInput,
			distributorInput : distributorInput,
			countryInput : countryInput,
			yearInput : yearInput,
			descriptionInput : descriptionInput,
			action : "create",
		});
		console.log("parametri su: " +params)
		$.ajax({
			url: 'Movies2Servlet?' + params,
			method : 'POST',
			dataType : 'json',
			success : function(response){
				if(response.status == 'success'){
					location.reload();
					alert(response.status);
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