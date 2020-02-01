$(document).ready(function(e){
	
	console.log("singl film")
	var idMovie = getUrlParameter('id');
	
	var div1 = $('#div1');
	
	
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
	
	
	getMovie();
	
	function addMovie(movie, loggedInUser){
		
		var title = $('<div class="col-md-8"><h2 class="movie-title">'+movie.title+'</h2></div>');
		var description = $('<div class="movie-summary"><p>'+movie.description+' </p></div>');
		var data = $('<ul class="movie-meta">'
				+'<li><strong>Directors:</strong>'+movie.directors+'</li>'
				+'<li><strong>Actors:</strong>'+movie.actors+'</li>'
			    +'<li><strong>Genre:</strong> '+movie.genre+'</li>'
			    +'<li><strong>Duration:</strong>'+movie.duration+'</li>'
			    +'<li><strong>Distributor:</strong>'+movie.distributor+'</li>'
		 		+'<li><strong>Country:</strong>'+movie.country+'</li>'
		 		+'<li><strong>Year:</strong>'+movie.year+'</li></div>');
		var pencil = $('<p><button data-toggle="modal" type="button" data-target="#editMovie" class="glyphicon glyphicon-pencil"></button></p> '+
					 	'<div id="editMovie" class="modal fade" role="dialog">'+
					 	'<div class="modal-dialog">'+
					 	'<div class="modal-content">'+
					      '<div class="modal-header">'+	
					      	'<button type="button" class="close" data-dismiss="modal">&times;</button>'+
					      	'<h4 class="modal-title">Edit Movie</h4>'+
					      '</div>'+
					      '<div class="modal-body">'+
					      '<div class="form-group">'+
					      		'<label for="title">Tittle:</label>'+
					      	'<input id="title" type="text" class="form-control" required value="'+movie.title+'">'+
					      	'</div>'+
					      '<div class="form-group">'+
					      	'<label for="directors">Directors:</label>'+
					      	'<input id="directors" type="text" class="form-control" required value="'+movie.directors+'">'+
					      	'</div>'+
					      '<div class="form-group">'+
				      		'<label for="genre">Genre:</label>'+
				      	'<input id="genre" type="text" class="form-control" required value="'+movie.genre+'">'+
				      	'</div>'+
				      	'<div class="form-group">'+
			      		'<label for="duration">Duration:</label>'+
			      	'<input id="duration" type="text" class="form-control" required value="'+movie.duration+'">'+
			      	'</div>'+
			      	'<div class="form-group">'+
		      		'<label for="tittle">Distributor:</label>'+
		      	'<input id="distributor" type="text" class="form-control" required value="'+movie.distributor+'">'+
		      	'</div>'+
		      	'<div class="form-group">'+
	      		'<label for="country">Country:</label>'+
	      	'<input id="country" type="text" class="form-control" required value="'+movie.country+'">'+
	      	'</div>'+
	      	'<div class="form-group">'+
      		'<label for="year">Year:</label>'+
      	'<input id="year" type="text" class="form-control" required value="'+movie.year+'">'+
      	'</div>'+
      	'<div class="form-group">'+
      	'<label for="desc">Description:</label>'+
      		'<input id="description" type="text" class="form-control" value="'+movie.description+'">'+
      '</div>'+
      
					 '<div class="modal-footer">'+
					 '<button type="button" class="btn btn-danger" style="float:left">Delete this movie</button>'+
					      '<button type="submit" id="editMovie" class="btn btn-success">Save</button>'+
					      	'</div>'+
					      		'</div></td>');
		
		div1.append(title);
		title.append(description);
		description.append(data);
		
		if(loggedInUser != null){
			if(loggedInUser.role=="ADMIN"){
				div1.append(pencil);
			}
		}
		
	
	}
	
	
	function getMovie(){
		
		var params = $.param({
			id : idMovie,
		}) ;
		console.log(params);
		$.ajax({
			url: 'MovieServlet?' + params,
			method: 'GET',
			dataType: 'json',
			success: function(response){
				if(response.status == "success"){
					showHide(response.loggedInUser)
					addMovie(response.movie, response.loggedInUser);
					console.log("naslov filna "+ response.movie.title)
				}else{
					alert.message;
				}
			},
			error: function(request, message, error){
				alert(error);
			}
		});
		
	};
	
	$('#movieEditForm').submit(function(e){
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
			id : idMovie,
			action : "update",
		});
		console.log("parametri su " + params)
		$.ajax({
			url: 'MovieServlet',
			data : params,
			method : 'POST',
			dataType : 'json',
			success : function(response){
				if(response.status == "success"){
					alert(response.message);
					console.log(response);
					location.reload();
				}else{
					alert(response.message)
				}
			},
			error: function(request, message, error){
			}
		});
	});
	
});