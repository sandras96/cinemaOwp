package cinemaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Movie;
import model.User;

public class MovieDAO {
	
	public static List<Movie> getAll() throws Exception {
		Connection conn = ConnectionManager.getConnection();
		List<Movie> movies = new ArrayList<Movie>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String query = "select * from movie where deleted = 0";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				int index = 1;
				Integer movieId = rs.getInt(index++);
				String title = rs.getString(index++);
				String directors = rs.getString(index++);
				String actors = rs.getString(index++);
				String genre = rs.getString(index++);
				Integer duration = rs.getInt(index++);
				String distributor = rs.getString(index++);
				String country = rs.getString(index++);
				Integer year = rs.getInt(index++);
				String description = rs.getString(index++);
				boolean deleted = rs.getBoolean(index++);

				Movie movie = new Movie(movieId, title, directors, actors, genre, duration, distributor, country, year,
						description, deleted);
				movies.add(movie);

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
		return movies;
	}
	
	public static Movie getById(Integer id) throws Exception {

		Connection conn = ConnectionManager.getConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String query = "select * from movie where deleted = 0 and id = ?";

			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			System.out.println(ps);

			rs = ps.executeQuery();

			if (rs.next()) {
				int index = 1;
				Integer movieId = rs.getInt(index++);
				String title = rs.getString(index++);
				String directors = rs.getString(index++);
				String actors = rs.getString(index++);
				String genre = rs.getString(index++);
				Integer duration = rs.getInt(index++);
				String distributor = rs.getString(index++);
				String country = rs.getString(index++);
				Integer year = rs.getInt(index++);
				String description = rs.getString(index++);
				boolean deleted = rs.getBoolean(index++);

				return new Movie(movieId, title, directors, actors, genre, duration, distributor, country, year,
						description, deleted);
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
	
	public static boolean createMovie(Movie movie) throws Exception {

		Connection conn = ConnectionManager.getConnection();

		PreparedStatement ps = null;

		try {

			String query = "insert into movie (title, directors, actors, genre, duration, distributor, country, year, description, deleted)"
					+ " VALUES (?,?,?,?,?,?,?,?,?,?);";
			int index = 1;
			ps = conn.prepareStatement(query);
			
			ps.setString(index++, movie.getTitle());
			ps.setString(index++, movie.getDirectors());
			ps.setString(index++, movie.getActors());
			ps.setString(index++, movie.getGenre());
			ps.setInt(index++, movie.getDuration());
			ps.setString(index++, movie.getDistributor());
			ps.setString(index++, movie.getCountry());
			ps.setInt(index++, movie.getYear());
			ps.setString(index++, movie.getDescription());
			ps.setBoolean(index++, movie.isDeleted());

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

	public static boolean updateMovie(Movie movie) throws Exception {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;

		try {
			String query = "update movie set title = ?, directors = ?, actors = ?, genre = ?, duration = ?,"
					+ "distributor = ?, country = ?, year = ?, description =?, deleted = ? where id=?";
			int index = 1;
			ps = conn.prepareStatement(query);
			ps.setString(index++, movie.getTitle());
			ps.setString(index++, movie.getDirectors());
			ps.setString(index++, movie.getActors());
			ps.setString(index++, movie.getGenre());
			ps.setInt(index++, movie.getDuration());
			ps.setString(index++, movie.getDistributor());
			ps.setString(index++, movie.getCountry());
			ps.setInt(index++,movie.getYear());
			ps.setString(index++, movie.getDescription());
			ps.setBoolean(index++, movie.isDeleted());
			ps.setInt(index++, movie.getId());
			
			
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
	
	
	public static boolean deleteMovie(Integer id) throws Exception {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "delete from movie where id = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			System.out.println(pstmt);

			return pstmt.executeUpdate() == 1;
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} 
		}
	}
}
