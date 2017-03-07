package com.coffee_beans.util;

public interface CBEventSource {
	public void addEventListener(CBEventListener listener);
	public void removeEventListener(CBEventListener listener);
}
