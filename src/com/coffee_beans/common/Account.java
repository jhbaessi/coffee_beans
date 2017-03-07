package com.coffee_beans.common;

import java.io.Serializable;

public class Account implements Serializable {
	private static final long serialVersionUID = 6984635402043818853L;
	
	private String username;
	private String email;
	private String password;
	
	public Account(String username, String email, String password) {
		setUsername(username);
		setEmail(email);
		setPassword(password);
	}
	
	public Account() {
		this(null, null, null);
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
}
