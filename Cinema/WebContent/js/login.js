$(document).ready(function() {

	
	$('#loginForm').submit(function(e){
		e.preventDefault();
		var x = $('#inputUsername').val().trim();
		var p = $('#inputPassword').val();
		var params = $.param({
			username: x,
			password: p
		});
		console.log(params)
		$.ajax({
			url: 'LoginServlet',
			data : params,
			method: 'POST',
			dataType: 'json',
			success: function(response){
				if(response.status == 'success'){
					location.reload();
				}else{
					alert(response.message)
				}
			},
			error: function(request, message, error){
				alert(error)
			}
		});
		$('#myModal').modal('toggle');
	});
	
	
	
	
	
});