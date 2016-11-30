package com.coffee_beans.gui;

import java.awt.CardLayout;

import javax.swing.JFrame;

public class GuiManager {
	private final int APP_WINDOW_WIDTH = 960;	// or 1024
	private final int APP_WINDOW_HEIGHT = 540;	// or 576
	
	private JFrame mainFrame;
	
	private enum PageIndex {
		SIGN_IN_PAGE("Sign in page"), 
		SIGN_UP_PAGE("Sign up page"),
		MESSENGER_PAGE("Messenger page");
		
		private final String text;
		
		private PageIndex(final String text) {
			this.text = text; 
		}
		
		@Override
		public String toString() {
			return text;
		}
	}
	
	public GuiManager() {
		buildGui();
		setPage(PageIndex.SIGN_IN_PAGE);
		mainFrame.setVisible(true);
	}
	
	private void buildGui() {
		mainFrame = new JFrame();
		mainFrame.getContentPane().setLayout(new CardLayout());
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(APP_WINDOW_WIDTH, APP_WINDOW_HEIGHT);
		
		mainFrame.add(SignInPanel.getInstance(), PageIndex.SIGN_IN_PAGE.toString());
		mainFrame.add(SignUpPanel.getInstance(), PageIndex.SIGN_UP_PAGE.toString());
		mainFrame.add(SignUpPanel.getInstance(), PageIndex.MESSENGER_PAGE.toString());
	}
	
	public void setPage(PageIndex page) {
		CardLayout layout = (CardLayout)mainFrame.getContentPane().getLayout();
		layout.show(mainFrame.getContentPane(), page.toString());
	}
}
