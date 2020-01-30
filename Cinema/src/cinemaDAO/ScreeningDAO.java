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

	public static List<Screening> getAll() throws Exception {

		Connection conn = ConnectionManager.getConnection();
		List<Screening> screenings = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String query = "select * from screening;";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				int index = 1;
				Integer screeningId = rs.getInt(index++);
				Movie movie = MovieDAO.getById(rs.getInt(index++));
				ScreenType screenType = ScreenTypeDAO.getById(rs.getInt(index++));
				Auditorium auditorium = AuditoriumDAO.getById(rs.getInt(index++));
				Date datetime = rs.getDate(index++);
				double ticketPrice = rs.getDouble(index++);
				User user = UserDAO.getByUsername(rs.getString(index++));
				Screening screening = new Screening(screeningId, movie, screenType, auditorium, datetime, ticketPrice,user);
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
			String query = "select * from screening where id =?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				int index = 1;
				Integer screeningId = rs.getInt(index++);
				Movie movie = MovieDAO.getById(rs.getInt(index++));
				ScreenType screenType = ScreenTypeDAO.getById(rs.getInt(index++));
				Auditorium auditorium = AuditoriumDAO.getById(rs.getInt(index++));
				Date datetime = rs.getDate(index++);
				double ticketPrice = rs.getDouble(index++);
				User user = UserDAO.getByUsername(rs.getString(index++));
				Screening screening = new Screening(screeningId, movie, screenType, auditorium, datetime, ticketPrice,
						user);
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

			String query = "insert into screening (movieId, screentypeId, auditoriumId, datetime, ticketPrice, user) values (?,?,?,?,?,?)";
			int index = 1;
			ps = conn.prepareStatement(query);
			ps.setInt(index++, screening.getMovie().getId());
			ps.setInt(index++, screening.getScreentype().getId());
			ps.setInt(index++, screening.getAuditorium().getId());
			ps.setDate(index++, new java.sql.Date(screening.getDatetime().getTime()));
			ps.setString(index++, screening.getUser().getUsername());
			

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
