package cinema;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

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
		String loggedInUsername = (String) session.getAttribute("loggedInUsername");
		User loggedInUser = null;
		if (loggedInUsername != null) {
			loggedInUser = (User) getServletContext().getAttribute(loggedInUsername);
		}

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
					if (user == null) {
						throw new Exception("User does not exist");
					}
				} else {
					if (loggedInUser.getUsername().equals(username)) {
						user = UserDAO.getByUsername(loggedInUser.getUsername());
					} else {
						throw new Exception("Access denied");
					}

				}
			}
			System.out.println("LoggedInUser je:" + loggedInUser.getUsername() + "pass" + loggedInUser.getUsername()
					+ "role" + loggedInUser.getRole());
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
		String loggedInUsername = (String) session.getAttribute("loggedInUsername");
		User loggedInUser = null;
		if (loggedInUsername != null) {
			loggedInUser = (User) getServletContext().getAttribute(loggedInUsername);
		}
		
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
				user = UserDAO.getByUsername(username);
				if (user == null) {
					throw new Exception("User does not exist");
				}
				if (loggedInUser == null) {
					throw new Exception("Access denied");
				} else {
					if (loggedInUser.getRole() == Role.ADMIN) {
						if (!newPass.equals("")) {
							user.setPassword(newPass);
						} else {
							user.setPassword(user.getPassword());
						}
						if (select.equals("")) {
							user.setRole(user.getRole());
						} else if (select.equals("USER")) {
							user.setRole(User.Role.USER);
						} else {
							user.setRole(User.Role.ADMIN);
						}

					} else {
						if (loggedInUser.getUsername().equals(username)) {
							if (!newPass.equals("")) {
								user.setPassword(newPass);
								user.setRole(user.getRole());
								System.out.println("nova lozinka je " + newPass);
							} else {
								user.setPassword(user.getPassword());
							}
						}

					}
				}
			
				if (!UserDAO.updateUser(user)) {
					throw new Exception("No changes");
				}
				
				message = "uspesno";
				status = "success";
				break;

			case "delete":
				User user1 = UserDAO.getByUsername(username);
				if(user1 == null) {
					throw new Exception("User does not exist");
				}
				if (loggedInUser == null || loggedInUser.getRole() != User.Role.ADMIN) {
					throw new Exception("Access denied!");
				} else 
					if(loggedInUser.getUsername().equals(user1.getUsername())) {
						throw new Exception("Access denied!");
					}else {
						
					if (!UserDAO.delete(username)) {
						user1.setDeleted(true);
						UserDAO.updateUser(user1);
					}
					getServletContext().removeAttribute(user1.getUsername());
					
					}
				status = "success";
				message = "Uspesno";

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
