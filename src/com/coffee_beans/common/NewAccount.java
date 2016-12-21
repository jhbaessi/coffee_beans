package com.coffee_beans.common;

import java.io.Serializable;

public class NewAccount implements Serializable {
	private static final long serialVersionUID = 6984635402043818851L;
	
	private String name;
	private String email;
	private String createPasswordField;
	private String confirmPasswordField;
	
	public NewAccount(String name, String email, String createPasswordField, String confirmPasswordField){
		this.name = name;
		this.email = email;
		this.createPasswordField = createPasswordField;
		this.confirmPasswordField = confirmPasswordField;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getCreatePasswordField(){
		return createPasswordField;
	}
	
	public String getconfirmPasswordField(){
		return confirmPasswordField;
	}
}
