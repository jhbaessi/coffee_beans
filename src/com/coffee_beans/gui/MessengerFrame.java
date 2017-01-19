package com.coffee_beans.gui;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.coffee_beans.common.Account;
import com.coffee_beans.util.CBEvent;
import com.coffee_beans.util.CBEvent.Events;
import com.coffee_beans.util.CBEventListener;
import com.coffee_beans.util.CBEventSource;
import com.coffee_beans.util.CBSerializer;

public class MessengerFrame extends JFrame implements CBEventListener, CBEventSource{
	private final int APP_WINDOW_WIDTH = 960;	// or 1024
	private final int APP_WINDOW_HEIGHT = 540;	// or 576
	
	private CBEventListener listener;
	private static MessengerFrame uniqueInstance;
	
	private JPanel container;
	
	private enum PageIndex {
		SIGN_IN_PAGE("Sign in page"),
		SIGN_UP_PAGE("Sign up page"),
		
		CONFIRM_ACCOUNT("Confirm account"),
		UPDATE_PASSWORD("Update password"),
		
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
	
	public static synchronized MessengerFrame getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new MessengerFrame();
			
		return uniqueInstance;
	}
	
	private MessengerFrame() {
		buildGui();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPage(PageIndex.SIGN_IN_PAGE);
		
		SignInPanel.getInstance().addEventListener(this);
		SignUpPanel.getInstance().addEventListener(this);
		AccountConfirmationPanel.getInstance().addEventListener(this);
		PasswordUpdatePanel.getInstance().addEventListener(this);
	}

	private void buildGui() {
		setLayout(new GridBagLayout());
		
		container = new JPanel();
		container.setLayout(new CardLayout());
		container.add(SignInPanel.getInstance(), PageIndex.SIGN_IN_PAGE.toString());
		container.add(SignUpPanel.getInstance(), PageIndex.SIGN_UP_PAGE.toString());
		container.add(AccountConfirmationPanel.getInstance(), PageIndex.CONFIRM_ACCOUNT.toString());
		container.add(PasswordUpdatePanel.getInstance(), PageIndex.UPDATE_PASSWORD.toString());
//		container.add(MessengerPane.getInstance(), PageIndex.MESSENGER_PAGE.toString());
		
		JScrollPane scrollPane = new JScrollPane(container);
		
		GridBagConstraints gbc =  new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = gbc.weighty = 1.0;
		add(scrollPane, gbc);
		
		setSize(APP_WINDOW_WIDTH, APP_WINDOW_HEIGHT);
	}
	
	public void setPage(PageIndex page) {
		CardLayout layout = (CardLayout)container.getLayout();
		layout.show(container, page.toString());
	}

	@Override
	public void addEventListener(CBEventListener listener) {
		this.listener = listener;
		
	}

	@Override
	public void removeEventListener(CBEventListener listener) {
		this.listener = null;
	}

	@Override
	public void eventReceived(CBEvent event) {
		// handle events
		
		// change page
		if (event.getEvent() == Events.REQ_SIGNIN_PAGE) {
			setPage(PageIndex.SIGN_IN_PAGE);
		} else if (event.getEvent() == Events.REQ_SIGNUP_PAGE) {
			setPage(PageIndex.SIGN_UP_PAGE);
		} else if (event.getEvent() == Events.REQ_ACCOUNT_CONFIRMATION_PAGE) {
			setPage(PageIndex.CONFIRM_ACCOUNT);
		} else if (event.getEvent() == Events.REQ_PASSWORD_UPDATE_PAGE) {
			setPage(PageIndex.UPDATE_PASSWORD);
		} else if (event.getEvent() == Events.REQ_MAIN_PAGE) {
			setPage(PageIndex.SIGN_IN_PAGE);
		
		// requests to server
		} else if (event.getEvent() == Events.REQ_VERIFYING_ACCOUNT) {
			// pass to event handler
			
			// for debugging
			Account account = (Account)CBSerializer.deserialize(event.getData());
			System.out.println("Deserialized data");
			System.out.println("email: " + account.getEmail());
			System.out.println("password: " + account.getPassword());
		
		} else if (event.getEvent() == Events.REQ_VERIFYING_USERINFO) {
			// for debugging
			Account account = (Account)CBSerializer.deserialize(event.getData());			
			System.out.println("Deserialized data");
			System.out.println("username: " + account.getUsername());
			System.out.println("email: " + account.getEmail());
			
			setPage(PageIndex.UPDATE_PASSWORD);
		} else if (event.getEvent() == Events.REQ_UPDATE_PASSWORD) {
			// for debugging
			Account account = (Account)CBSerializer.deserialize(event.getData());
			System.out.println("Deserialized data");
			System.out.println("password: " + account.getPassword());
			
			setPage(PageIndex.SIGN_IN_PAGE);			
		} else {
			System.out.println("Unknown event");
		}
	}
}
