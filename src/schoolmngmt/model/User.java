package schoolmngmt.model;

import java.util.ArrayList;

public abstract class User {
	private String username;
	private ArrayList<String> roles = new ArrayList<String>();
	
	public String getUsername() {
		return username;
	}
	
	protected void AddRole(String role) {
		this.roles.add(role);
	}
	
	public User(String username) {
		this.username = username;
	}
	
	public Boolean hasRole(String role) {
		return roles.contains(role);
	}
}
