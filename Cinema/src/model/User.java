package model;

import java.util.Date;

public class User {

	public enum Role {
		ADMIN, USER,
	}

	private String username;
	private String password;
	private Date datetime;
	private Role role;
	private boolean deleted;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String username, String password, Date datetime, Role role, boolean deleted) {
		super();
		this.username = username;
		this.password = password;
		this.datetime = datetime;
		this.role = role;
		this.deleted = deleted;
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}
