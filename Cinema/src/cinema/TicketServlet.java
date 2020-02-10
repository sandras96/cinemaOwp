package cinema;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import cinemaDAO.ScreeningDAO;
import cinemaDAO.SeatDAO;
import cinemaDAO.TicketDAO;
import model.Screening;
import model.Seat;
import model.Ticket;
import model.User;

/**
 * Servlet implementation class TicketServlet
 */
public class TicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TicketServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String movieId = request.getParameter("movieId");
		long date = new Date().getTime();
		List<Screening> screeningsForMovie = new ArrayList<>();
		String message = "";
		String status = "";
		
		System.out.println("id za film je " + movieId);
		
		try {
			if(movieId == null) {
				throw new Exception("Movie does not exist");
			}
			screeningsForMovie = ScreeningDAO.getByMovieId(Integer.parseInt(movieId), date);
			
			System.out.println("projekcije za film su " + screeningsForMovie);
			
			message = "uspesno";
			status = "success";
			
		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
		}
		
		Map<String, Object> data = new HashMap<>();
		data.put("message", message);
		data.put("status", status);
		data.put("screeningsForMovie", screeningsForMovie);
		
		
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
		
		String seatsParam = request.getParameter("seats");
		String auditoriumId = request.getParameter("auditoriumId");
		String screeningId = request.getParameter("screeningId");
		
		String message = "";
		String status = "";
		
		try {
			if (seatsParam == null) {
				throw new Exception("seat is not provided");
			}
			if (loggedInUser == null) {
				throw new Exception("access denied");
			}
			Screening screening = ScreeningDAO.getById(Integer.parseInt(screeningId));
			
			List<Integer> availableSeats = SeatDAO.getAvailableByAudId(Integer.parseInt(auditoriumId),Integer.parseInt(screeningId));
			List<String> seatsIds = new ArrayList<String>(Arrays.asList(seatsParam.split(",")));
			
			List<Integer> realSeatsIds = new ArrayList<Integer>();
			for (String seatId : seatsIds) {
				realSeatsIds.add(Integer.parseInt(seatId));
			}
			List<Ticket> tickets = new ArrayList<Ticket>();
			
			for (Integer seatid : realSeatsIds) {
				if (!availableSeats.contains(seatid)) {
					throw new Exception("seat is not available");
				}
				Ticket ticket = new Ticket();
				ticket.setUser(loggedInUser);
				ticket.setScreening(screening);
				ticket.setDatetime(new Date());
				ticket.setSeat(SeatDAO.getById(seatid));
				ticket.setDeleted(false);
				
				tickets.add(ticket);
			}
			
			if (!TicketDAO.createTickets(tickets)) {
				throw new Exception("error");
			}
			
			message="uspesno";
			status = "success";
			
				
		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
			message = e.getMessage();
		}
		
		Map<String, Object> data = new HashMap<>();
		data.put("message", message);
		data.put("status", status);
		
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	
	}

}
