package cinemaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Auditorium;
import model.Movie;
import model.Seat;

public class SeatDAO {
	
	public static Seat getById(Integer id) throws Exception {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String query = "select * from seat where id = ?";

			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			System.out.println(ps);

			rs = ps.executeQuery();

			if (rs.next()) {
				int index = 1;
				Integer seatId = rs.getInt(index++);
				Integer seatNO = rs.getInt(index++);
				Auditorium auditorium = AuditoriumDAO.getById(rs.getInt(index++));
				Seat seat = new Seat(seatId, seatNO, auditorium);

				return seat;
			}
		}

		finally {
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
		return null;
	}
	
	public static List<Seat> getByAudId(Integer auditoriumId, Integer screeningId) throws Exception{
		Connection conn = ConnectionManager.getConnection();
		List<Seat> seats = new ArrayList<>();	
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String query = "select s.* from seat s where s.auditoriumId = ? and s.id not in " + 
					"    	(select t.seatId from ticket t left outer join screening s on t.screeningId = s.id" + 
					"    	left outer join seat se on se.id = t.seatId" + 
					"    	where t.screeningId = ? )";  
			
			
			ps = conn.prepareStatement(query);
			ps.setInt(1, auditoriumId);
			ps.setInt(2, screeningId);
			rs = ps.executeQuery();
			while(rs.next()) {
				int index = 1;
				Integer seatId = rs.getInt(index++);
				Integer seatNO = rs.getInt(index++);
				Auditorium auditorium = AuditoriumDAO.getById(rs.getInt(index++));
				Seat seat = new Seat(seatId, seatNO, auditorium);
				seats.add(seat);
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
		return seats;
	}

	
	public static List<Integer> getAvailableByAudId(Integer auditoriumId, Integer screeningId) throws Exception{
		Connection conn = ConnectionManager.getConnection();
		List<Integer> seats = new ArrayList<>();	
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String query = "select s.id from seat s where s.auditoriumId = ? and s.id not in " + 
					"    	(select t.seatId from ticket t left outer join screening s on t.screeningId = s.id" + 
					"    	left outer join seat se on se.id = t.seatId" + 
					"    	where t.screeningId = ? )";  
			
			
			ps = conn.prepareStatement(query);
			ps.setInt(1, auditoriumId);
			ps.setInt(2, screeningId);
			rs = ps.executeQuery();
			while(rs.next()) {
				int index = 1;
				Integer seatId = rs.getInt(index++);
				seats.add(seatId);
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
		return seats;
	}
}
