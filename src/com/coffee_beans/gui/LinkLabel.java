package com.coffee_beans.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;

import javax.swing.JLabel;

public class LinkLabel extends JLabel implements MouseListener {
	private URI uri;
	
	public LinkLabel() {
		this(null, null);
	}
	
	public LinkLabel(URI uri) {
		this(uri.toString(), uri);
	}
	
	public LinkLabel(String text, URI uri) {
		setText(text);
		setForeground(Color.BLUE);
		this.uri = uri;
		
		addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			Desktop.getDesktop().browse(uri);	// for test
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setCursor(Cursor.getDefaultCursor());
		
	}

}
