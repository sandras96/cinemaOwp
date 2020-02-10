package cinema;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import cinemaDAO.MovieDAO;
import cinemaDAO.UserDAO;
import cinemaDAO.Util;
import model.Movie;
import model.User;
import model.User.Role;

/**
 * Servlet implementation class MovieServlet
 */
public class MovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String loggedInUsername = (String) session.getAttribute("loggedInUsername");
		User loggedInUser = null;
		if (loggedInUsername != null) {
			loggedInUser = (User) getServletContext().getAttribute(loggedInUsername);
		}
		
		String movieId = request.getParameter("id");
		
		Movie movie = null;
		String message = "";
		String status = "";
		
		try {
			movie = MovieDAO.getById(Integer.parseInt(movieId));
			if(movie == null) {
				throw new Exception("Movie does not exist");
			}
			message = "uspesno";
			status = "success";
		} catch (Exception e) {
			message = e.getMessage();
			status = "failure";
		}
		
		Map<String, Object> data = new HashMap<>();
		data.put("message", message);
		data.put("status", status);
		data.put("movie", movie);
		data.put("loggedInUser", loggedInUser);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String loggedInUsername = (String) session.getAttribute("loggedInUsername");
		User loggedInUser = null;
		if (loggedInUsername != null) {
			loggedInUser = (User) getServletContext().getAttribute(loggedInUsername);
		}
		
		String idMovie = request.getParameter("id");
		String title = request.getParameter("titleInput");
		String directors = request.getParameter("directorsInput");
		String actors = request.getParameter("actorsInput");
		String genre = request.getParameter("genreInput");
		String duration = request.getParameter("durationInput");
		String distributor = request.getParameter("distributorInput");
		String country = request.getParameter("countryInput");
		String year = request.getParameter("yearInput");
		String description = request.getParameter("descriptionInput");
		String action = request.getParameter("action");
		Movie movie = null;
		String message = "";
		String status = "";
		
		try {
			movie = MovieDAO.getById(Integer.parseInt(idMovie));
			if(movie == null) {
				throw new Exception("Movie does not exist");
			}
			switch (action) {
			case "update":
				if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
					throw new Exception("Access denied");
				}else {
					movie.setTitle(title);
					movie.setDirectors(directors);
					movie.setActors(actors);
					movie.setGenre(genre);
					if(!Util.isNumeric(duration)) {
						throw new Exception("Please put number for duration");
					}else {
						movie.setDuration(Integer.parseInt(duration));
					}
					
					movie.setDistributor(distributor);
					movie.setCountry(country);
					if(!Util.isNumeric(year)) {
						throw new Exception("Please put number for year");
					}else {
						movie.setYear(Integer.parseInt(year));
					}
					movie.setDescription(description);
					MovieDAO.updateMovie(movie);
					
					movie = MovieDAO.getById(Integer.parseInt(idMovie));
				}
				message = "uspesno";
				status= "success";
				
				break;
				
				
			case "delete" ://update movie set deleted true
				Movie movie1 = MovieDAO.getById(Integer.parseInt(idMovie));
				if(loggedInUser == null || loggedInUser.getRole() != User.Role.ADMIN) {
					throw new Exception("Access denied!");
				}else {
					
				if(!MovieDAO.deleteMovie(Integer.parseInt(idMovie))) {
					movie1.setDeleted(true);
					MovieDAO.updateMovie(movie1);
				}
				}
				
				status = "success";
				message = "Uspesno obradjen zahtev";
				
				break;
			}
			
		} catch (Exception e) {
			message = e.getMessage();
			status = "failure";
		}
		
		Map<String, Object> data = new HashMap<>();
		data.put("message", message);
		data.put("status", status);
		data.put("movie", movie);
		data.put("loggedInUser", loggedInUser);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

}
