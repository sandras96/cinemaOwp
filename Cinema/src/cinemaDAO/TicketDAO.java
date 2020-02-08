package cinemaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Ticket;

public class TicketDAO {
	
	public static List<Ticket> getAll() throws Exception{
		Connection conn = ConnectionManager.getConnection();
		
		List<Ticket> tickets = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String query = "select * from ticket";
			
		} finally {
			// TODO: handle finally clause
		}
		
		
		
		
		
		
		return null;
	}

}
