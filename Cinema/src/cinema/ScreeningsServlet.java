package cinema;

import java.io.IOException;
import java.util.Date;
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

import cinemaDAO.AuditoriumDAO;
import cinemaDAO.MovieDAO;
import cinemaDAO.ScreenTypeDAO;
import cinemaDAO.ScreeningDAO;
import cinemaDAO.Util;
import model.Auditorium;
import model.Movie;
import model.ScreenType;
import model.Screening;
import model.User;
import model.User.Role;

/**
 * Servlet implementation class ScreeningsServlet
 */
public class ScreeningsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScreeningsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		User loggedInUser = (User)session.getAttribute("loggedInUser");
		List<Screening> screenings = new ArrayList<>();
		List<Movie> movies = new ArrayList<>();
		long dateFrom;
		long dateTo;
		
		String movieSearch = Util.createParam(request.getParameter("movieSearch"));
	//	String datetimeSearch = Util.createParam(request.getParameter("datetimeSearch"));
		String screentypeSearch = Util.createParam(request.getParameter("screentypeSearch"));
		String auditoriumSearch = Util.createParam(request.getParameter("auditoriumSearch"));
		/*double ticketPrice1 = Double.valueOf(request.getParameter("ticketPrice1"));
		double ticketPrice2 = Double.valueOf(request.getParameter("ticketPrice2"));
		
		/*long dateFrom = Util.createDateParam(request.getParameter("dateFrom"), "min");
		long dateTo = Util.createDateParam(request.getParameter("dateTo"), "max");*/
		
		/*long param = Long.parseLong(request.getParameter("dateFrom"));
		long param2 = Long.parseLong(request.getParameter("dateTo"));*/
		
		String param = request.getParameter("dateFrom");
		String param2 = request.getParameter("dateTo");
		
		if(param.equals("")) {
			dateFrom = 0;
		}else {
			dateFrom = Long.parseLong(param);
		}
		
		if(param2.equals("")) {
			dateTo = Long.MAX_VALUE;
		}else {
			dateTo = Long.parseLong(param2);
		}
		System.out.println("datumi suu" + dateFrom + "  " + dateTo);
	//	String ticketPriceSearch = Util.createParam(request.getParameter("ticketPriceSearch"));
		String sortBy = request.getParameter("sortBy");
		
		System.out.println("sortby je " + sortBy);
		String message = "";
		String status = "";
		
		try {
			
			movies = MovieDAO.getAll();
			screenings = ScreeningDAO.getAll(movieSearch, screentypeSearch, auditoriumSearch, dateFrom, dateTo, sortBy);
		
			
			
			message = "uspesno";
			status = "success";
			
			
		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
			
		}
		
		Map<String, Object> data = new HashMap<>();
		data.put("message", message);
		data.put("status", status);
		data.put("screenings", screenings);
		data.put("loggedInUser", loggedInUser);
		data.put("movies", movies);
		
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
		HttpSession session = request.getSession();
		User loggedInUser = (User)session.getAttribute("loggedInUser");
		
		String movieId = request.getParameter("movieId");
		String auditoriumId = request.getParameter("auditoriumId");
		String screentypeId = request.getParameter("screentypeId");
		String datetime = request.getParameter("datetime");
		
		String ticketPrice = request.getParameter("ticketPrice");
		String action = request.getParameter("action");
		Screening screening = null;
		String message = "";
		String status = "";
		
		try { 
			if(loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
				throw new Exception("Access denied");
			}
			switch (action) {
		case "add":
			
			Movie movie = MovieDAO.getById(Integer.parseInt(movieId));
			Auditorium auditorium = AuditoriumDAO.getById(Integer.parseInt(auditoriumId));
			ScreenType screentype = ScreenTypeDAO.getById(Integer.parseInt(screentypeId));
			screening = new Screening();
			Date date = new Date(Long.parseLong(datetime));
			screening.setMovie(movie);
			screening.setAuditorium(auditorium);
			screening.setScreentype(screentype);
			screening.setDatetime(date);
			screening.setTicketPrice(Double.valueOf(ticketPrice));
			screening.setUser(loggedInUser);
			ScreeningDAO.createScreening(screening);
			
			System.out.println("Nova projekcija je +" + screening);
			message = "uspesno";
			status = "success";
			break;

		default:
			break;
		}
			
		} catch (Exception e) {
			message = e.getMessage();
			status = "failure";
		}
	
		Map<String, Object> data = new HashMap<>();
		data.put("message", message);
		data.put("status", status);
		data.put("screening", screening);
		data.put("loggedInUser", loggedInUser);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}
	

}
