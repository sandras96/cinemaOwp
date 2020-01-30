package model;

public class Seat {
	
	private int seatNO;
	private Auditorium auditorium;
	
	public Seat() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Seat(int seatNO, Auditorium auditorium) {
		super();
		this.seatNO = seatNO;
		this.auditorium = auditorium;
	}

	public int getSeatNO() {
		return seatNO;
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
