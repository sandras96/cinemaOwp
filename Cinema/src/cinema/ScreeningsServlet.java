package cinema;

import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import cinemaDAO.AuditoriumDAO;
import cinemaDAO.FormatDate;
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
import java.text.DateFormat; 
import java.text.SimpleDateFormat;

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
		String loggedInUsername = (String) session.getAttribute("loggedInUsername");
		User loggedInUser = null;
		if (loggedInUsername != null) {
			loggedInUser = (User) getServletContext().getAttribute(loggedInUsername);
		}
		
		List<Screening> screenings = new ArrayList<>();
		
		List<Movie> movies = new ArrayList<>();
	
		
		String movieSearch = Util.createParam(request.getParameter("movieSearch"));
		String screentypeSearch = Util.createParam(request.getParameter("screentypeSearch"));
		String auditoriumSearch = Util.createParam(request.getParameter("auditoriumSearch"));
		/*double ticketPrice1 = Double.valueOf(request.getParameter("ticketPrice1"));
		double ticketPrice2 = Double.valueOf(request.getParameter("ticketPrice2"));*/
		
		
	
	
		long dateFrom = Util.dateParam(request.getParameter("dateFrom"));
		long dateTo = Util.dateParamTo(request.getParameter("dateTo"));
		
		

		System.out.println("datumi suu" + dateFrom + "  " + dateTo);
	//	String ticketPriceSearch = Util.createParam(request.getParameter("ticketPriceSearch"));
		String sortBy = request.getParameter("sortBy");
		
		System.out.println("sortby je " + sortBy);
		String message = "";
		String status = "";
		
		try {
			if(dateFrom > dateTo) {
				throw new Exception("wrong date");
			}
			movies = MovieDAO.getAll();
			screenings = ScreeningDAO.getAll(movieSearch, screentypeSearch, auditoriumSearch, dateFrom, dateTo, sortBy);
		
			
			message = "uspesno";
			status = "success";
			
			
		} catch (Exception e) {
			message = e.getMessage();
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
		String loggedInUsername = (String) session.getAttribute("loggedInUsername");
		User loggedInUser = null;
		if (loggedInUsername != null) {
			loggedInUser = (User) getServletContext().getAttribute(loggedInUsername);
		}
		
		String movieId = request.getParameter("movieId");
		String auditoriumId = request.getParameter("auditoriumId");
		String screentypeId = request.getParameter("screentypeId");
		
		long datetime = Util.dateParam(request.getParameter("datetime"));
		
		Date date = new Date(datetime);
		System.out.println("long je " + date);
		
		String ticketPrice = request.getParameter("ticketPrice");
		String action = request.getParameter("action");
		Screening screening = null;
		String message = "";
		String status = "";
		
		try { 
			if(loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
				throw new Exception("Access denied");
			}
			Calendar calendar = FormatDate.dateToCalendar(date);
			long datetimeStart = ((calendar).getTime()).getTime();
			switch (action) {
		case "add":
			Date date2 = new Date();
			if(date.compareTo(date2) < 0) {
				throw new Exception("wrong date");
			}
			Movie movie = MovieDAO.getById(Integer.parseInt(movieId));
			Auditorium auditorium = AuditoriumDAO.getById(Integer.parseInt(auditoriumId));
			
			Calendar calendar2 = FormatDate.dateEndCalendar(date);
			long datetimeEnd = ((calendar2).getTime()).getTime();
			long movieEnd = movie.getDuration() * 60000 + date.getTime();
			
			List<Auditorium> freeauditoriums = AuditoriumDAO.getAllFreeAuditoriums(Integer.parseInt(screentypeId), datetime, datetimeStart, datetimeEnd, movieEnd);
			boolean validAuditorium = false;
			for (Auditorium a : freeauditoriums) {
				if(auditorium.getId() == a.getId()) {
					validAuditorium = true;
					break;
				}
			}
			if (!validAuditorium ) { 
				throw new Exception("Auditorium is not available");
				}
			ScreenType screentype = ScreenTypeDAO.getById(Integer.parseInt(screentypeId));
			screening = new Screening();
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
