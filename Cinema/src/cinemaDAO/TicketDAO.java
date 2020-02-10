package cinemaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Movie;
import model.Screening;
import model.Seat;
import model.Ticket;
import model.User;
import model.User.Role;

public class TicketDAO {
	
	public static boolean createTickets(List<Ticket> tickets) throws Exception {

		Connection conn = ConnectionManager.getConnection();

		PreparedStatement ps = null;

		try {
			conn.setAutoCommit(false);
			conn.commit();
			String query = "insert into ticket (screeningId, seatId, datetime, user, deleted) values (?,?,?,?,?)";
			for (Ticket ticket : tickets) {
				ps = conn.prepareStatement(query);
				int index = 1;
				
				ps.setInt(index++, ticket.getScreening().getId());
				ps.setInt(index++, ticket.getSeat().getId());
				ps.setLong(index++, ticket.getDatetime().getTime());
				ps.setString(index++, ticket.getUser().getUsername());
				ps.setBoolean(index, ticket.isDeleted());
				
				ps.executeUpdate();
				ps.close();
			}
			
			conn.commit();
			return true;
			

		} finally {
			try { 
				conn.setAutoCommit(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			try {
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public static List<Ticket> getAllByUsername(String username) throws Exception{
		Connection conn = ConnectionManager.getConnection();
		List<Ticket> tickets = new ArrayList<>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String query = "select * from ticket where user = ? ";
			ps = conn.prepareStatement(query);
			ps.setString(1, username);
			rs = ps.executeQuery();
			while (rs.next()) {
				int index = 1;
				int ticketId = rs.getInt(index++);
				Screening screening = ScreeningDAO.getById(rs.getInt(index++));
				Seat seat = SeatDAO.getById(rs.getInt(index++));
				Date datetime = new Date(rs.getLong(index++));
				User user = UserDAO.getByUsername(rs.getString(index++));
				boolean deleted = rs.getBoolean(index++);

				Ticket ticket = new Ticket(ticketId, screening, seat, datetime, user, deleted);
				tickets.add(ticket);

			}
			
		} finally {
			try { 
				conn.setAutoCommit(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			try {
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return tickets;
		
	}
	
	public static boolean delete(Integer id, long date) throws Exception {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement ps = null;
		try {
			String query = "delete from ticket where id in"
					+ " (select t.id from ticket t left outer join screening s on s.id = t.screeningId where t.id = ?"
					+ " and s.datetime > ?);";

			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			ps.setLong(2, date);
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
