package com.coffee_beans.util;

import java.util.EventListener;

public interface CBEventListener extends EventListener {
	public void eventReceived(CBEvent event);
}