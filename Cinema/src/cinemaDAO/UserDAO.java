package cinemaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.User;
import model.User.Role;

public class UserDAO {

	public static List<User> getAll() throws Exception {
		List<User> users = new ArrayList<>();
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String query = "select * from user;";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				int index = 1;
				String username = rs.getString(index++);
				String password = rs.getString(index++);
				Date datetime = FormatDate.parseDate(rs.getString(index++));
				Role role = Role.valueOf(rs.getString(index++));

				User user = new User(username, password, datetime, role);
				users.add(user);

			}

		} finally {
			try {
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return users;
	}

	public static User getByUsername(String username) throws Exception {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String query = "select * from user where username= ?";
			System.out.println("kveri je " + query);
			ps = conn.prepareStatement(query);
			ps.setString(1, username);
			System.out.println("ps je " + ps);
			rs = ps.executeQuery();
			

			if (rs.next()) {
				int index = 1;
				String username1 = rs.getString(index++);
				String password = rs.getString(index++);
				Date datetime = FormatDate.parseDate(rs.getString(index++));
				Role role = Role.valueOf(rs.getString(index++));

				return new User(username1, password, datetime, role);

			}
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;

	}

	public static boolean createUser(User user) throws Exception {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;

		try {
			String query = "insert into user (username, password, datetime, role) values (?,?,?,?)";
			int index = 1;
			ps = conn.prepareStatement(query);
			ps.setString(index++, user.getUsername());
			ps.setString(index++, user.getPassword());
			ps.setString(index++, FormatDate.formatDate(user.getDatetime()));
			ps.setString(index++, user.getRole().toString());

			return ps.executeUpdate() == 1;

		} finally {
			try {
				ps.close();
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
		}

	}

	public static boolean updateUser(User user) throws Exception {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;

		try {
			String query = "update user set password = ?, datetime = ?, role = ? where username=?";
			int index = 1;
			ps = conn.prepareStatement(query);
			ps.setString(index++, user.getPassword());
			ps.setString(index++, FormatDate.formatDate(user.getDatetime()));
			ps.setString(index++, user.getRole().toString());
			ps.setString(index++, user.getUsername());

			return ps.executeUpdate() == 1;

		} finally {
			try {
				ps.close();
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
		}

	}
	
	public static List<User> getAllBySearch(String searchParam, String orderBy, String direction, String searchSelect) throws Exception{
		Connection conn = ConnectionManager.getConnection();
		List<User> users = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String order = orderBy + " " + direction + ";";
			String param;
			String query;
			if(searchParam == null && searchSelect == null) {
				query = "select * from user order by " + order + ":";
			}else{
				param = "like '%" + searchParam + "%'";
				if(searchSelect.equals("username")) {
					query = "select * from user where username " + param + "order by " + order + ";";
				}else if(searchSelect.equals("role")){
					query = "select * from user where role " + param + "order by " + order + ";";
				}else {
					query = "select * from user where username " + param + "order by " + order + ";";
				}
				
			}
			
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			System.out.println("Koji je kveriii " + query);
			while (rs.next()) {
				int index = 1;
				String username = rs.getString(index++);
				String password = rs.getString(index++);
				Date datetime = FormatDate.parseDate(rs.getString(index++));
				Role role = Role.valueOf(rs.getString(index++));

				User user = new User(username, password, datetime, role);
				users.add(user);

			}
			
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return users;
	}

}
