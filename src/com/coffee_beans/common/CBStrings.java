package com.coffee_beans.common;

public enum CBStrings {
	EMAIL						("Email"),
	PASSWORD					("Password"),
	NEW_PASSWORD				("New password"),
	CONFIRM_PASSWORD			("Confirm password"),
	SIGN_IN						("Sign in"),
	SIGN_UP						("Sign up"),
	FOR_GOT_PASSWORD			("For got password"),
	USERNAME					("Username"),
	CREATE_PASSWORD				("Create a password"),
	TERMS_OF_SERVICE			("terms of service"),
	PRIVACY_POLICY				("privacy policy"),
	NOTICE_SIGN_UP				("By clicking \"Sign Up\", you agree to our"),
	REQUEST_ACCOUNT_VERIFICATION("Request for account verification"),
	UPDATE_PASSWORD				("Update password");
	
	private final String text;
	
	private CBStrings(final String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return text;
	}
}
