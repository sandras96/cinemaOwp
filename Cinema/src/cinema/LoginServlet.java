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

import cinemaDAO.UserDAO;
import model.User;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = null;
		String message = "";
		String status = "";
		
		try {
			user = UserDAO.getByUsername(username);
			if(user == null) {
				throw new Exception("User does not exist");
			}
			if(!user.getPassword().equals(password)) {
				throw new Exception("Wrong password");
			}
			/*if(user.isDeleted()) {
				throw new Exception("User deleted!"); ???????
			}*/
			HttpSession session = request.getSession();
			session.setAttribute("loggedInUser", user);
			
			System.out.println("Logovani user je:" + user.getUsername() + user.getPassword());
			message = "uspesno";
			status = "success";
			
		} catch (Exception e) {
			message = e.getMessage();
			status = "failure";
		}
		
		Map<String, Object> data = new HashMap<>();
		data.put("message", message);
		data.put("status",status);
		data.put("loggedInUser", user );
	
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

}
