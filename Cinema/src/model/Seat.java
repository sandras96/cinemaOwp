package model;

public class Seat {
	
	private int id;
	private int seatNO;
	private Auditorium auditorium;
	
	public Seat() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Seat(int id, int seatNO, Auditorium auditorium) {
		super();
		this.id = id;
		this.seatNO = seatNO;
		this.auditorium = auditorium;
	}

	public int getSeatNO() {
		return seatNO;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setSeatNO(int seatNO) {
		this.seatNO = seatNO;
	}

	public Auditorium getAuditorium() {
		return auditorium;
	}

	public void setAuditorium(Auditorium auditorium) {
		this.auditorium = auditorium;
	}
	
	

}
