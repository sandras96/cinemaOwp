package cinemaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Auditorium;
import model.Movie;
import model.ScreenType;
import model.Screening;
import model.User;

public class ScreeningDAO {

	public static List<Screening> getAll(String movieParam, String scrtypeParam,
			String auditParam, long dateFrom, long dateTo, String sortBy) throws Exception {

		Connection conn = ConnectionManager.getConnection();
		List<Screening> screenings = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String orderBy;
			if(sortBy.equals("")) {
				orderBy = "m.title asc";
			}else {
				orderBy = sortBy;
			}
			
			String query = "select scr.* from screening scr" + 
					"    left join movie m on scr.movieId = m.id" + 
					"    left join screenType sct on scr.screenTypeId = sct.id" + 
					"    left join auditorium a on scr.auditoriumId = a.id" + 
					" where" + 
					"    a.name like ? and" + 
					"    sct.name like ? and" + 
					"    m.title like ? and "+
					"	 scr.deleted = 0 and "+
					"	 scr.datetime > ? and " + // datum od
					"	 scr.datetime < ? " +     // datum do
					"    order by " + orderBy + ";";
			
			ps = conn.prepareStatement(query);
			ps.setString(1, auditParam);
			ps.setString(2, scrtypeParam);
			ps.setString(3, movieParam);
			ps.setLong(4, dateFrom);
			ps.setLong(5, dateTo);
		
			rs = ps.executeQuery();
			System.out.println("kveri je " + query + "order by je "+ orderBy);
			while (rs.next()) {
				int index = 1;
				Integer screeningId = rs.getInt(index++);
				Movie movie = MovieDAO.getById(rs.getInt(index++));
				ScreenType screenType = ScreenTypeDAO.getById(rs.getInt(index++));
				Auditorium auditorium = AuditoriumDAO.getById(rs.getInt(index++));
				Date datetime = new Date(rs.getLong(index++));
				double ticketPrice = rs.getDouble(index++);
				User user = UserDAO.getByUsername(rs.getString(index++));
				boolean deleted = rs.getBoolean(index++);
				Screening screening = new Screening(screeningId, movie, screenType, auditorium, datetime, ticketPrice,user, deleted);
				screenings.add(screening);
				
			}

		} finally {
			try {
				ps.close();
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
			try {
				rs.close();
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
		}
		return screenings;

	}

	public static Screening getById(Integer id) throws Exception {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String query = "select * from screening where deleted = 0 and id =?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				int index = 1;
				Integer screeningId = rs.getInt(index++);
				Movie movie = MovieDAO.getById(rs.getInt(index++));
				ScreenType screenType = ScreenTypeDAO.getById(rs.getInt(index++));
				Auditorium auditorium = AuditoriumDAO.getById(rs.getInt(index++));
				Date datetime = new Date(rs.getLong(index++));
				double ticketPrice = rs.getDouble(index++);
				User user = UserDAO.getByUsername(rs.getString(index++));
				boolean deleted = rs.getBoolean(index++);
				Screening screening = new Screening(screeningId, movie, screenType, auditorium, datetime, ticketPrice,
						user, deleted);
				return screening;
			}

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

		return null;
	}

	public static boolean createScreening(Screening screening) throws Exception {

		Connection conn = ConnectionManager.getConnection();

		PreparedStatement ps = null;

		try {

			String query = "insert into screening (movieId, screentypeId, auditoriumId, datetime, ticketPrice, user, deleted) values (?,?,?,?,?,?,?)";
			int index = 1;
			ps = conn.prepareStatement(query);
			ps.setInt(index++, screening.getMovie().getId());
			ps.setInt(index++, screening.getScreentype().getId());
			ps.setInt(index++, screening.getAuditorium().getId());
			ps.setLong(index++, screening.getDatetime().getTime());
			ps.setDouble(index++, screening.getTicketPrice());
			ps.setString(index++, screening.getUser().getUsername());
			ps.setBoolean(index++, screening.isDeleted());
			

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

	public static boolean delete(Integer id) throws Exception {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement ps = null;
		try {
			String query = "delete from screening where id = ?";

			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			System.out.println(ps);

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
	
	
	

}
