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
import model.User.Role;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		String username = request.getParameter("usr");
		User user = null;
		String message = "";
		String status = "";

		try {
			if (loggedInUser == null) {
				throw new Exception("Access denied");
			} else {
				if (loggedInUser.getRole() == Role.ADMIN) {
					user = UserDAO.getByUsername(username);
				} else {
					if (loggedInUser.getUsername().equals(username)) {
						user = UserDAO.getByUsername(loggedInUser.getUsername());
					} else {
						throw new Exception("Access denied");
					}

				}
			}
			System.out.println("LoggedInUser je:" + loggedInUser.getUsername() +"pass"+ loggedInUser.getUsername()+"role" + loggedInUser.getRole());
			message = "uspesno";
			status = "success";
		} catch (Exception e) {
			message = e.getMessage();
			status = "failure";
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("message", message);
		data.put("status", status);
		data.put("user", user);
		data.put("loggedInUser", loggedInUser);

		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		String action = request.getParameter("action");
		String select = request.getParameter("select");
		String newPass = request.getParameter("newPass");
		String username = request.getParameter("usr");
		User user = null;
		String message = "";
		String status = "";
		System.out.println("akcije je " + action);

		try {
			switch (action) {
			case "update":
				if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
					throw new Exception("Access denied");
				} else {
					user = UserDAO.getByUsername(username);
					if (user == null) {
						throw new Exception("User does not exist");
					}
					if (!newPass.equals("")) {
						user.setPassword(newPass);
						System.out.println("nova lozinka je " + newPass);
					}

					if (select.equals("")) {
						user.setRole(user.getRole());
					} else if (select.equals("USER")) {
						user.setRole(User.Role.USER);
					} else {
						user.setRole(User.Role.ADMIN);
					}
				}
				
				if(!UserDAO.updateUser(user)) {
					throw new Exception("No changes");
				}
				System.out.println("Izmenio sam useraa " + user.getPassword());
				message = "uspesno";
				status = "success";
				break;
			}

		} catch (Exception e) {
			message = e.getMessage();
			status = "failure";
			
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("message", message);
		data.put("status", status);
		data.put("user", user);
		data.put("loggedInUser", loggedInUser);

		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

}
