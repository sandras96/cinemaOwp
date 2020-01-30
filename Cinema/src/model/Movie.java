package model;

public class Movie {
	
	private int id;
	private String title;
	private String directors;
	private String actors;
	private String genre; 
	private int duration;
	private String distributor;
	private String country;
	private int year;
	private String description;
	
	
	public Movie() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Movie(int id, String title, String directors, String actors, String genre, int duration, String distributor,
			String country, int year, String description) {
		super();
		this.id = id;
		this.title = title;
		this.directors = directors;
		this.actors = actors;
		this.genre = genre;
		this.duration = duration;
		this.distributor = distributor;
		this.country = country;
		this.year = year;
		this.description = description;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDirectors() {
		return directors;
	}


	public void setDirectors(String directors) {
		this.directors = directors;
	}


	public String getActors() {
		return actors;
	}


	public void setActors(String actors) {
		this.actors = actors;
	}


	public String getGenre() {
		return genre;
	}


	public void setGenre(String genre) {
		this.genre = genre;
	}


	public int getDuration() {
		return duration;
	}


	public void setDuration(int duration) {
		this.duration = duration;
	}



	public String getDistributor() {
		return distributor;
	}


	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	
	
	

}
