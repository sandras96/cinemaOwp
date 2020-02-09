package cinemaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Movie;
import model.Screening;
import model.Seat;
import model.Ticket;
import model.User;

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
			

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				}
			throw e;
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
	
	
	
}
