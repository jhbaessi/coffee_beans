package com.coffee_beans.gui;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.coffee_beans.common.Account;
import com.coffee_beans.util.CBEvent;
import com.coffee_beans.util.CBEvent.Events;
import com.coffee_beans.util.CBEventListener;
import com.coffee_beans.util.CBSerializer;

public class GuiManager implements CBEventListener{
	private final int APP_WINDOW_WIDTH = 960;	// or 1024
	private final int APP_WINDOW_HEIGHT = 540;	// or 576
	
	private JFrame mainFrame;
	private JPanel mainPanel;
	
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
		
		SignInPanel.getInstance().addEventListener(this);
		
		setPage(PageIndex.SIGN_IN_PAGE);
		
		mainFrame.setVisible(true);
	}
	
	private void buildGui() {
		mainPanel = new JPanel(new CardLayout());	
		mainPanel.add(SignInPanel.getInstance(), PageIndex.SIGN_IN_PAGE.toString());
		mainPanel.add(SignUpPanel.getInstance(), PageIndex.SIGN_UP_PAGE.toString());
		mainPanel.add(MessengerPane.getInstance(), PageIndex.MESSENGER_PAGE.toString());
		
		mainFrame = new JFrame();		
		mainFrame.getContentPane().setLayout(new CardLayout());
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(APP_WINDOW_WIDTH, APP_WINDOW_HEIGHT);
		
		mainFrame.add(mainPanel);
	}
	
	public void setPage(PageIndex page) {
		CardLayout layout = (CardLayout)mainPanel.getLayout();
		layout.show(mainPanel, page.toString());
	}

	@Override
	public void eventReceived(CBEvent event) {
		// handle events
		
		if (event.getEvent() == Events.REQ_SIGNUP_PAGE) {
			setPage(PageIndex.SIGN_UP_PAGE);
		} else if (event.getEvent() == Events.REQ_VERIFYING_ACCOUNT) {
			// pass to event handler
			Account account = (Account)CBSerializer.deserialize(event.getData());
			
			System.out.println("Deserialized data");
			System.out.println("email: " + account.getEmail());
			System.out.println("password: " + account.getPassword());
		} else {
			System.out.println("Unknown event");
		}
	}
}
