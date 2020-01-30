package cinemaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.ScreenType;

public class ScreenTypeDAO {
	
	public static List<ScreenType> getAll() throws Exception {

		Connection conn = ConnectionManager.getConnection();
		List<ScreenType> screenTypes = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String query = "select * from screenType";
			ps = conn.prepareStatement(query);

			rs = ps.executeQuery();

			while (rs.next()) {
				int index = 1;
				Integer screenTypeId = rs.getInt(index++);
				String name = rs.getString(index++);
				ScreenType screenType = new ScreenType(screenTypeId, name);
				screenTypes.add(screenType);
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
		return screenTypes;

	}

	public static ScreenType getById(Integer id) throws Exception {

		Connection conn = ConnectionManager.getConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String query = "select * from screentype where id = ?";

			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			System.out.println(ps);

			rs = ps.executeQuery();

			if (rs.next()) {
				int index = 1;
				Integer screenTypeId = rs.getInt(index++);
				String name = rs.getString(index++);

				return new ScreenType(screenTypeId, name);
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

	
	
	public static List<ScreenType> getAllScreenTypes(Integer auditoriumId) throws Exception {
		Connection conn = ConnectionManager.getConnection();
		List<ScreenType> screenTypes = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String query = "select scr.* from audScrType a join screenType scr on a.screenTypeId=scr.id where a.auditoriumId = ? ";

			ps = conn.prepareStatement(query);
			ps.setInt(1, auditoriumId);
			System.out.println(ps);

			rs = ps.executeQuery();

			if (rs.next()) {
				int index = 1;
				Integer screenTypeId = rs.getInt(index++);
				String name = rs.getString(index++);

				ScreenType screenType = new ScreenType(screenTypeId, name);
				screenTypes.add(screenType);

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

		return screenTypes;

	}


}
