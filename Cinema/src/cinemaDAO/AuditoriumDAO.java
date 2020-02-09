package cinemaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Auditorium;
import model.ScreenType;

public class AuditoriumDAO {

	public static List<Auditorium> getAll() throws Exception{
		
		List<Auditorium> auditoriums = new ArrayList<>();
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String query = "select * from auditorium;";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int index = 1;
				Integer auditoriumId = rs.getInt(index++);
				String name = rs.getString(index++);
				Auditorium auditorium = new Auditorium(auditoriumId, name);
				auditoriums.add(auditorium);
			}
			
		}finally {
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
		
		return auditoriums;
		
	}
	
	public static Auditorium getById(Integer id) throws Exception{
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		
		try {
			String query = "select * from auditorium where id=?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				int index = 1;
				Integer auditoriumId = rs.getInt(index++);
				String name = rs.getString(index++);

				return new Auditorium(auditoriumId, name);

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
	
	public static List<Auditorium> getAllAuditoriumsforType(Integer screntypeId) throws Exception {
		Connection conn = ConnectionManager.getConnection();
		List<Auditorium> auditoriums = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String query = "select aud.* from auditorium aud inner join audScrType a on aud.id = a.auditoriumId where a.screenTypeId = ? ";

			ps = conn.prepareStatement(query);
			ps.setInt(1, screntypeId);
			System.out.println(ps);

			rs = ps.executeQuery();

			while (rs.next()) {
				int index = 1;
				Integer auditoriumId = rs.getInt(index++);
				String name = rs.getString(index++);

				Auditorium auditorium = new Auditorium(auditoriumId, name);
				auditoriums.add(auditorium);

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

		return auditoriums;

	}
	
	public static List<Auditorium> getAllFreeAuditoriums(Integer screntypeId, long datetime, long dateStart ) throws Exception {
		Connection conn = ConnectionManager.getConnection();
		List<Auditorium> auditoriums = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			System.out.println("adtum je " + datetime);
			String query = "select a.* from auditorium a " + 
											"left outer join audScrType ast on a.id = ast.auditoriumId " + 
											"where ast.screenTypeId = ? and a.id not in " + 
											"(select distinct s.auditoriumId from screening s " + 
											"left outer join audScrType ast on ast.screenTypeId = s.screenTypeId " + 
											"left outer join movie m on s.movieId = m.id " + 
											"where s.datetime > ? and "+
											"((s.datetime + m.duration*60000) > ?));"; 

			ps = conn.prepareStatement(query);
			System.out.println("kveri za datume je " + query);
			ps.setInt(1, screntypeId);
			ps.setLong(2, dateStart);
			//ps.setInt(3, screntypeId);
			ps.setLong(3, datetime);
			
			System.out.println(ps);

			rs = ps.executeQuery();

			while (rs.next()) {
				int index = 1;
				Integer auditoriumId = rs.getInt(index++);
				String name = rs.getString(index++);

				Auditorium auditorium = new Auditorium(auditoriumId, name);
				auditoriums.add(auditorium);

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

		return auditoriums;

	}
}
