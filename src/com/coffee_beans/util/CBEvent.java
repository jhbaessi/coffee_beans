package com.coffee_beans.util;

import java.util.EventObject;

public class CBEvent extends EventObject {
	private static final long serialVersionUID = -5572054785385399216L;

	public enum Events {
		MOVE_TO_SIGNUP_PAGE;
	}
	
	private Events event;
	
	public CBEvent(Object source, Events event) {
		super(source);
		this.event = event;
	}
	
	public Events getEvent() {
		return event;
	}
}
