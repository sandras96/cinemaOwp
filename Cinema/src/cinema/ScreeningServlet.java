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

import com.fasterxml.jackson.databind.ObjectMapper;

import cinemaDAO.ScreeningDAO;
import cinemaDAO.SeatDAO;
import model.Screening;
import model.Seat;

/**
 * Servlet implementation class ScreeningServlet
 */
public class ScreeningServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScreeningServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		Screening screening = null;
		List<Seat> seats = new ArrayList<>();
		String message = "";
		String status = "";
		try {
			screening = ScreeningDAO.getById(Integer.parseInt(id));
			seats = SeatDAO.getByAudId(screening.getAuditorium().getId(),screening.getId());
			
			
			message="uspesno";
			status = "success";
			
		} catch (Exception e) {
			//message = e.getMessage();
			e.printStackTrace();
			status = "failure";
		}
		
		
		Map<String, Object> data = new HashMap<>();
		data.put("message", message);
		data.put("status", status);
		data.put("screening", screening);
		data.put("seats", seats);
		
		
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
		doGet(request, response);
	}

}
