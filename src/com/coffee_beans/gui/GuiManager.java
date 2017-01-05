package com.coffee_beans.gui;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.coffee_beans.common.Account;
import com.coffee_beans.common.NewAccount;
import com.coffee_beans.util.CBEvent;
import com.coffee_beans.util.CBEvent.Events;
import com.coffee_beans.util.CBEventListener;
import com.coffee_beans.util.CBSerializer;

public class GuiManager implements CBEventListener{
	private final int APP_WINDOW_WIDTH = 960;	// or 1024
	private final int APP_WINDOW_HEIGHT = 540;	// or 576
	
	private JFrame mainFrame;
	private JPanel mainPanel;
	private JScrollPane mainScrollPane;
	
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
	
	public GuiManager() {
		buildGui();
		
		SignInPanel.getInstance().addEventListener(this);
		SignUpPanel.getInstance().addEventListener(this);
		AccountConfirmationPanel.getInstance().addEventListener(this);
		PasswordUpdatePanel.getInstance().addEventListener(this);
		
		setPage(PageIndex.SIGN_IN_PAGE);
		
		mainFrame.setVisible(true);
	}
	
	private void buildGui() {
		mainPanel = new JPanel(new CardLayout());
		mainPanel.add(SignInPanel.getInstance(), PageIndex.SIGN_IN_PAGE.toString());
		mainPanel.add(SignUpPanel.getInstance(), PageIndex.SIGN_UP_PAGE.toString());
		mainPanel.add(AccountConfirmationPanel.getInstance(), PageIndex.CONFIRM_ACCOUNT.toString());
		mainPanel.add(PasswordUpdatePanel.getInstance(), PageIndex.UPDATE_PASSWORD.toString());
		mainPanel.add(MessengerPane.getInstance(), PageIndex.MESSENGER_PAGE.toString());
		
		mainScrollPane = new JScrollPane(mainPanel);
		mainScrollPane.setViewportView(mainPanel);
		mainScrollPane.getViewport().setView(mainPanel);
		
		mainFrame = new JFrame();		
		mainFrame.getContentPane().setLayout(new CardLayout());
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(APP_WINDOW_WIDTH, APP_WINDOW_HEIGHT);
		
		mainFrame.add(new JScrollPane(mainPanel));
	}
	
	public void setPage(PageIndex page) {
		CardLayout layout = (CardLayout)mainPanel.getLayout();
		layout.show(mainPanel, page.toString());
	}

	@Override
	public void eventReceived(CBEvent event) {
		// handle events
		if (event.getEvent() == Events.REQ_SIGNIN_PAGE) {
			setPage(PageIndex.SIGN_IN_PAGE);
		} else if (event.getEvent() == Events.REQ_SIGNUP_PAGE) {
			setPage(PageIndex.SIGN_UP_PAGE);
		} else if (event.getEvent() == Events.REQ_ACCOUNT_CONFIRMATION_PAGE) {
			setPage(PageIndex.CONFIRM_ACCOUNT);
		} else if (event.getEvent() == Events.REQ_MAIN_PAGE) {
			setPage(PageIndex.SIGN_IN_PAGE);

		} else if (event.getEvent() == Events.REQ_VERIFYING_ACCOUNT) {
			// pass to event handler
			
			// for debugging
			Account account = (Account)CBSerializer.deserialize(event.getData());
			System.out.println("Deserialized data");
			System.out.println("email: " + account.getEmail());
			System.out.println("password: " + account.getPassword());
		} else if (event.getEvent() == Events.REQ_NEW_ACCOUNT){
			NewAccount newAccount = (NewAccount)CBSerializer.deserialize(event.getData());
			
			System.out.println("name: " + newAccount.getName());
			System.out.println("email: " + newAccount.getEmail());
			System.out.println("createPasswordField: " + newAccount.getCreatePasswordField());
			System.out.println("confirmPasswordField: " + newAccount.getconfirmPasswordField());
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
