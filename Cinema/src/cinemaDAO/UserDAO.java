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

	public static List<User> getAll(String usernameParam, String roleParam, String sortBy) throws Exception {
		List<User> users = new ArrayList<>();
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String orderBy;
			if(sortBy.equals("")) {
				orderBy = "username desc";
			}else{
				orderBy = sortBy;
				
			}
			System.out.println("order je " + orderBy);
			String query = "select * from user where deleted = 0 and username like ? and role like ? order by " + orderBy +";";
			ps = conn.prepareStatement(query);
			ps.setString(1, usernameParam);
			ps.setString(2, roleParam);
			rs = ps.executeQuery();
			System.out.println("KVERI SORT je " + query + "orderby je " + orderBy);
			while (rs.next()) {
				int index = 1;
				String username = rs.getString(index++);
				String password = rs.getString(index++);
				Date datetime = FormatDate.parseDate(rs.getString(index++));
				Role role = Role.valueOf(rs.getString(index++));
				boolean deleted = rs.getBoolean(index++);

				User user = new User(username, password, datetime, role, deleted);
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
			String query = "select * from user where deleted = 0 and username= ?";
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
				boolean deleted = rs.getBoolean(index++);

				return new User(username1, password, datetime, role, deleted);

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
			String query = "insert into user (username, password, datetime, role, deleted) values (?,?,?,?,?)";
			int index = 1;
			ps = conn.prepareStatement(query);
			ps.setString(index++, user.getUsername());
			ps.setString(index++, user.getPassword());
			ps.setString(index++, FormatDate.formatDate(user.getDatetime()));
			ps.setString(index++, user.getRole().toString());
			ps.setBoolean(index++, user.isDeleted());

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
			String query = "update user set password = ?, datetime = ?, role = ?, deleted = ? where username=?";
			int index = 1;
			ps = conn.prepareStatement(query);
			ps.setString(index++, user.getPassword());
			ps.setString(index++, FormatDate.formatDate(user.getDatetime()));
			ps.setString(index++, user.getRole().toString());
			ps.setBoolean(index++, user.isDeleted());
			ps.setString(index++, user.getUsername());
			

			boolean retval = ps.executeUpdate() == 1;
			return retval;

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
				boolean deleted = rs.getBoolean(index++);

				User user = new User(username, password, datetime, role,deleted);
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
	
	public static boolean delete(String username) throws Exception {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "delete from user where username = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, username);
			System.out.println(pstmt);

			return pstmt.executeUpdate() == 1;
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} 
		}
	}

	
}
