package com.coffee_beans.gui;

public enum CBStrings {
	EMAIL				("Email"),
	PASSWORD			("Password"),
	SIGN_IN				("Sign in"),
	SIGN_UP				("Sign up"),
	FOR_GOT_PASSWORD	("For got password"),
	TERMS_OF_SERVICE	("terms of service"),
	PRIVACY_POLICY		("privacy policy");
	
	private final String text;
	
	private CBStrings(final String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return text;
	}
}
