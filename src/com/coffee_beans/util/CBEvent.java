package com.coffee_beans.util;

import java.util.EventObject;

public class CBEvent extends EventObject {
	private static final long serialVersionUID = -5572054785385399216L;

	public enum Events {
		// page change
		REQ_SIGNIN_PAGE,
		REQ_SIGNUP_PAGE,
		REQ_ACCOUNT_CONFIRMATION_PAGE,
		REQ_PASSWORD_UPDATE_PAGE,
		REQ_MAIN_PAGE,
		
		// requests to server
		REQ_VERIFYING_ACCOUNT,		// check up on email and password
		REQ_VERIFYING_USERINFO,		// check up on username and email
		REQ_NEW_ACCOUNT,
		REQ_UPDATE_PASSWORD,

		// response from server
		RES_VERIFYING_ACCOUNT,
		RES_NEW_ACCOUNT,
		
		// etc
		REQ_TERMS_OF_SERVICE_PAGE,
		REQ_PRIVACY_POLICY_PAGE,
	}
	
	private Events	event;
	private byte[]	bytes;
	
	public CBEvent(Object source, Events event, byte[] bytes) {
		super(source);
		this.event = event;
		this.bytes = bytes;
	}
	
	public CBEvent(Object source, Events event) {
		this(source, event, null);
	}
	
	public Events getEvent() {
		return event;
	}
	
	public byte[] getData() {
		return bytes;
	}
}