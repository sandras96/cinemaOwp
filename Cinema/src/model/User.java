package model;

import java.util.Date;

public class User {

	public enum Role {
		ADMIN,
		USER,
	}
	private String username;
	private String password;
	private Date datetime;
	private Role role;
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public User(String username, String password, Date datetime, Role role) {
		super();
		this.username = username;
		this.password = password;
		this.datetime = datetime;
		this.role = role;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	
}
