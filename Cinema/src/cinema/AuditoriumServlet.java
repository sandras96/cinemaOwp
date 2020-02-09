package cinema;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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

import cinemaDAO.AuditoriumDAO;
import cinemaDAO.FormatDate;
import cinemaDAO.MovieDAO;
import model.Auditorium;
import model.Movie;
import model.User;

/**
 * Servlet implementation class AuditoriumServlet
 */
public class AuditoriumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuditoriumServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String idScreenType = request.getParameter("idScreenType");
		long datetime = Long.parseLong(request.getParameter("datetime"));
		
		System.out.println("datetime sa fronta je " + datetime);
		
		Date date = new Date(datetime);
		
		List<Auditorium> auditoriums = new ArrayList<>();
		
		String message = "";
		String status = "";
		
		try {
			
			Calendar calendar = FormatDate.dateToCalendar(date);
			long datetimeStart = ((calendar).getTime()).getTime();
			
			if(idScreenType != null) {
				
				auditoriums = AuditoriumDAO.getAllFreeAuditoriums(Integer.parseInt(idScreenType), datetime, datetimeStart);
			}
			
			message = "uspesno";
			status = "success";
			
		} catch (Exception e) {
			e.getMessage();
			status = "failure";
		}
	
		Map<String, Object> data = new HashMap<>();
		data.put("message", message);
		data.put("status", status);
		data.put("auditoriums", auditoriums);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		
	}

}
