package com.coffee_beans.util;

import java.util.EventObject;

public class CBEvent extends EventObject {
	private static final long serialVersionUID = -5572054785385399216L;

	public enum Events {
		REQ_SIGNUP_PAGE,
		REQ_FIND_PASSWORD_PAGE,
		REQ_MAIN_PAGE,
		REQ_VERIFYING_ACCOUNT,
		REQ_NEW_ACCOUNT,
		REQ_TERMS_OF_SERVICE_PAGE,
		REQ_PRIVACY_POLICY_PAGE,

		RES_VERIFYING_ACCOUNT,
		RES_NEW_ACCOUNT,
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