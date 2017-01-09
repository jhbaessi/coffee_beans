package com.coffee_beans.util;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;

public class HtmlLoader {
	public static boolean loadHtml(URI uri) {
		boolean result = false;
		
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(uri);
				result = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public static boolean loadHtml(File file) {
		return loadHtml(file.toURI());		
	}
}
