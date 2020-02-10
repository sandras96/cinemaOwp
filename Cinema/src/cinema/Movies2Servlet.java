package cinema;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import cinemaDAO.MovieDAO;
import cinemaDAO.Util;
import model.Movie;
import model.User;
import model.User.Role;

/**
 * Servlet implementation class Movies2Servlet
 */
public class Movies2Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Movies2Servlet() {
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
		
		List<Movie> movies = new ArrayList<>();
		
		String titleSearch = Util.createParam(request.getParameter("titleSearch"));
		String genreSearch = Util.createParam(request.getParameter("genreSearch"));
		int durationSearchMin = Util.createParamBetweenMin(request.getParameter("durationSearch"));
		int durationSearchMax = Util.createParamBetweenMax(request.getParameter("durationSearch"));
		String distributorSearch = Util.createParam(request.getParameter("distributorSearch"));
		String countrySearch = Util.createParam(request.getParameter("countrySearch"));
		int yearSearchMin = Util.createParamBetweenMin(request.getParameter("yearSearch"));
		int yearSearchMax = Util.createParamBetweenMax(request.getParameter("yearSearch"));
		String sortBy = request.getParameter("sortBy");
		
		String message = "";
		String status = "";
		
		try {
			movies = MovieDAO.getAllParam(titleSearch, genreSearch, durationSearchMin,durationSearchMax, distributorSearch, countrySearch, yearSearchMin, yearSearchMax, sortBy);
			
			
			message="uspesno";
			status = "success";
			
		}catch (Exception e) {
			message = e.getMessage();
			status = "failure";
			}
		Map<String, Object> data = new HashMap<>();
		data.put("message", message);
		data.put("status", status);
		data.put("movies", movies);
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
		String message = "";
		String status = "";
		Movie newMovie = null;
		System.out.println("Logovani je " + loggedInUser);
		System.out.println("naziv filma je" + title);
		try {
			switch (action) {
			case "create":
				if(loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
					throw new Exception("Access denied!");
				}
				if("".equals(title) || "".equals(directors) || "".equals(actors) || "".equals(genre) || 
					"".equals(duration) || "".equals(distributor) || "".equals(country) || "".equals(year) || "".equals(description) ) {
					throw new Exception("Please fill all the fields");
				}
				if (!Util.isNumeric(year)) {
					throw new Exception("Please put number for year");
				}
				if(!Util.isNumeric(duration)) {
					throw new Exception("Please put number for duration");
				}
				
					newMovie = new Movie();
					newMovie.setTitle(title);
					newMovie.setDirectors(directors);
					newMovie.setActors(actors);
					newMovie.setGenre(genre);
					newMovie.setDuration(Integer.parseInt(duration));
					newMovie.setDistributor(distributor);
					newMovie.setCountry(country);
					newMovie.setYear(Integer.parseInt(year));
					newMovie.setDescription(description);
					MovieDAO.createMovie(newMovie);
					System.out.println("Novi film je" + newMovie.getTitle() + "id novog filma je " + newMovie.getId());
					
				
				
				message = "uspesno";
				status = "success";
				
				break;

			default:
				break;
			}
		} catch(Exception e){
			message = e.getMessage();
			status = "failure";
		}
		
		Map<String, Object> data = new HashMap<>();
		data.put("message", message);
		data.put("status", status);
		data.put("movie", newMovie);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

}
