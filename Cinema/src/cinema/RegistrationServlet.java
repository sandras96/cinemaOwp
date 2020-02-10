package cinema;

import java.io.IOException;
import java.util.Date;
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
 * Servlet implementation class RegistrationServlet
 */
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServlet() {
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
		String usernameReg = request.getParameter("usernameReg");
		String passwordReg = request.getParameter("passwordReg");
		String passwordRep = request.getParameter("passwordRep");
		User newUser = null;
		String message = "";
		String status = "";
		try {
			
			if("".equals(usernameReg) || "".equals(passwordReg) || "".equals(passwordRep) ) 
				throw new Exception("Please fill all fields.");
				
			if(!passwordReg.equals(passwordRep)) 
				throw new Exception("Passwords do not match.");
			
			User existingUser = UserDAO.getByUsername(usernameReg);
			if(existingUser != null) {
				throw new Exception("User already exists.");
			}
			newUser = new User();
			newUser.setUsername(usernameReg);
			newUser.setPassword(passwordRep);
			newUser.setRole(User.Role.USER);
			newUser.setDatetime(new Date());
			newUser.setDeleted(false);
			UserDAO.createUser(newUser);
			
			HttpSession session = request.getSession();
			session.setAttribute("loggedInUsername", newUser.getUsername());
			getServletContext().setAttribute(newUser.getUsername(), newUser);
			
			System.out.println("User je " + newUser.getUsername());
			
			message = "Uspesno ste se registrovali";
			status = "success";
			
		}catch (Exception e){
			message = e.getMessage();
			status = "failure";
			}
	
		Map<String, Object> data = new HashMap<>();
		data.put("message", message);
		data.put("status", status);
		data.put("loggedInUser", newUser);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
		
	}
}
