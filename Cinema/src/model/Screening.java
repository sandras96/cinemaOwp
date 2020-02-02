package model;

import java.util.Date;

public class Screening {

	private int id;
	private Movie movie;
	private ScreenType screentype;
	private Auditorium auditorium;
	private Date datetime;
	private double ticketPrice;
	private User user;
	private boolean deleted;

	public Screening() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Screening(int id, Movie movie, ScreenType screentype, Auditorium auditorium, Date datetime,
			double ticketPrice, User user, boolean deleted) {
		super();
		this.id = id;
		this.movie = movie;
		this.screentype = screentype;
		this.auditorium = auditorium;
		this.datetime = datetime;
		this.ticketPrice = ticketPrice;
		this.user = user;
		this.deleted = deleted;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public ScreenType getScreentype() {
		return screentype;
	}

	public void setScreentype(ScreenType screentype) {
		this.screentype = screentype;
	}

	public Auditorium getAuditorium() {
		return auditorium;
	}

	public void setAuditorium(Auditorium auditorium) {
		this.auditorium = auditorium;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}
