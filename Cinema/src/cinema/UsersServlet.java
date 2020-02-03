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
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import cinemaDAO.UserDAO;
import cinemaDAO.Util;
import model.User;
import model.User.Role;

/**
 * Servlet implementation class UsersServlet
 */
public class UsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsersServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		List<User> users = new ArrayList<>();
		
		String usernameSearch = Util.createParam(request.getParameter("usernameSearch"));
		String roleSearch = Util.createParam(request.getParameter("roleSearch"));
		String sortBy = request.getParameter("sortBy");
		
		System.out.println("parametri su" + usernameSearch + roleSearch + "sort je " + sortBy);
/*		String inputSearch = request.getParameter("inputSearch");
		String selectSearch = request.getParameter("select");*/
		/*String direction = request.getParameter("direction");
		String orderBy = request.getParameter("orderBy");
		String defaultOrderBy = "username";
		String defaultDirection = "desc";
		*/
		
		String message = "";
		String status = "";
		
//		System.out.println("search param je " + inputSearch + "selec serarch je " + selectSearch ); 
		try {
			
			if(loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
				throw new Exception("Access denied!");
			}
			users = UserDAO.getAll(usernameSearch, roleSearch, sortBy);
			
			//	users = UserDAO.getAll(usernameSearch, roleSearch, defaultOrderBy, defaultDirection);
			
		/*	
				users = UserDAO.getAllBySearch(inputSearch, orderBy, direction,selectSearch);0-
			}else {
				users = UserDAO.getAllBySearch(inputSearch, defaultOrderBy, defaultDirection, selectSearch);
			}*/
			
			
			message = "uspesno";
			status = "success";
		} catch (Exception e) {
			message = e.getMessage();
			status = "failure";
		}
		
		Map<String, Object> data = new HashMap<>();
		data.put("message", message);
		data.put("status", status);
		data.put("users", users);
		data.put("loggedInUser", loggedInUser);
		
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
