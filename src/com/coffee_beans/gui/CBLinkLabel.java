package com.coffee_beans.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

public class CBLinkLabel extends JLabel {
	
	public CBLinkLabel() {
		this(null, null);
	}
	
	public CBLinkLabel(String text) {
		this(text, null);
	}
	
	public CBLinkLabel(String text, MouseListener listener) {
		setOpaque(true);
		setForeground(Color.BLUE);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		setText(text);
		addMouseListener(listener);
	}
}
