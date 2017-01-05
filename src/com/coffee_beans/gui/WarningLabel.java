package com.coffee_beans.gui;

import java.awt.Color;

import javax.swing.JLabel;

public class WarningLabel extends JLabel {

	public enum WarningStrings{
		NO_WARNING(" "),
		
		INVALID_EMAIL_FORM("Email is invalid taken."),
		EXISTING_EMAIL("Email is invalid or already taken."),
		NOT_MATCH_PASSWORD("These passswords don't match"),
		NOT_FOUND_USERINFO("Sorry, can't find that username and email.");
		
		private final String text;
		
		private WarningStrings(final String text) {
			this.text = text;
		}
		
		@Override
		public String toString() {
			return text;
		}
	}
	
	private WarningStrings warning;
	
	public WarningLabel() {
		this(WarningStrings.NO_WARNING);
	}
	
	public WarningLabel(WarningStrings warning) {
		setOpaque(true);
		setForeground(Color.RED);
		
		setWarning(warning);
	}

	public void setWarning(WarningStrings warning) {
		setText(warning.toString());
		this.warning = warning;
	}
	
	public WarningStrings getWarning() {
		return warning;
	}
}
