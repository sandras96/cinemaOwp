package model;

import java.util.List;

public class Auditorium {

	private int id;
	private String name;
	private List<ScreenType> screentypes;
	
	
	public Auditorium() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Auditorium(int id, String name, List<ScreenType> screentypes) {
		super();
		this.id = id;
		this.name = name;
		this.screentypes = screentypes;
	}
	
	public Auditorium(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ScreenType> getScreentypes() {
		return screentypes;
	}
	public void setScreentypes(List<ScreenType> screentypes) {
		this.screentypes = screentypes;
	}
	
	
}
