package com.coffee_beans.util;

import java.util.EventObject;

public class CBEvent extends EventObject {
	private static final long serialVersionUID = -5572054785385399216L;

	public enum Events {
		MOVE_TO_SIGNUP_PAGE,
		
		VERIFY_ACCOUNT;
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