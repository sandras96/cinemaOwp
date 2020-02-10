package cinema;

import java.io.IOException;
import java.util.ArrayList;
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

import cinemaDAO.TicketDAO;
import model.Ticket;
import model.User;
import model.User.Role;

/**
 * Servlet implementation class TicketsForUserServlet
 */
public class TicketsForUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TicketsForUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		String username = request.getParameter("username");
		List<Ticket> tickets = new ArrayList<>();
		String message = "";
		String status = "";
		
		try {
			tickets = TicketDAO.getAllByUsername(username);
			
			message="uspesno";
			status= "success";
			
			
		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
		}
		Map<String, Object> data = new HashMap<>();
		data.put("message", message);
		data.put("status", status);
		data.put("tickets", tickets);
		
		
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
		String ticketId = request.getParameter("ticketId");
		
		String message = "";
		String status = "";
		
		try {
			if(loggedInUser == null || loggedInUser.getRole()!= Role.ADMIN) {
				throw new Exception("Access denied");
			}
			Date date = new Date();
			long datetime = date.getTime();
			if(!TicketDAO.delete(Integer.parseInt(ticketId), datetime)) {
				throw new Exception("error");
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
		
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

}
