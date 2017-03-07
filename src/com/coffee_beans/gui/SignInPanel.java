package com.coffee_beans.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

import com.coffee_beans.common.Account;
import com.coffee_beans.common.CBStrings;
import com.coffee_beans.util.CBEvent;
import com.coffee_beans.util.CBEvent.Events;
import com.coffee_beans.util.CBEventListener;
import com.coffee_beans.util.CBEventSource;
import com.coffee_beans.util.CBSerializer;

public class SignInPanel extends JPanel implements CBEventSource {
	private static final int GAP_VERTICAL = 20;
	
	private static final int LOGO_WIDTH = 200;
	private static final int LOGO_HEIGHT = 100;

	private static final int TEXTFIELD_WIDTH = 200;
	private static final int TEXTFIELD_HEIGHT = 30;
	
	private static final int SEPARATOR_WIDTH = 400;
	private static final int SEPARATOR_HEIGHT = 20;
	
	private static SignInPanel uniqueInstance;
	
	private JTextField emailField;
	private JPasswordField passwordField;
	
	private CBEventListener listener;
	
	public static synchronized SignInPanel getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new SignInPanel();
			
		return uniqueInstance;
	}
	
	private SignInPanel() {
		buildGui();
	}
	
	private void buildGui() {
		// logo label
		JLabel logoLabel = null;
		try {
			Image logoImg = ImageIO.read(new File("images/Logo.png"))
								.getScaledInstance(LOGO_WIDTH, LOGO_HEIGHT, Image.SCALE_SMOOTH);
			
			logoLabel = new JLabel(new ImageIcon(logoImg));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// email components
		JLabel emailLabel = new JLabel(CBStrings.EMAIL.toString());
		
		emailField = new JTextField();
		emailField.setMaximumSize(new Dimension(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT));

		// password components
		JLabel passwordLabel = new JLabel(CBStrings.PASSWORD.toString());

		passwordField = new JPasswordField();
		passwordField.setMaximumSize(new Dimension(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT));

		// sign-in button
		JButton signInButton = new JButton(CBStrings.SIGN_IN.toString());
		
		signInButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String email = emailField.getText();
				String password = String.valueOf(passwordField.getPassword());
				
				if (email.isEmpty()) {
					
				} else if (password.isEmpty()) {
					
				} else {
					// request for account verification to server
					if (listener != null) {
						byte[] bytes = CBSerializer.serialize(new Account(null, email, password));
						if (bytes != null) {
							listener.eventReceived(new CBEvent(this, Events.REQ_VERIFYING_ACCOUNT, bytes));
						} else {
							// failed to serialized
						}
					}
				}
			}
		});
		
		// divider line
		CBSeparator separator = new CBSeparator(SwingConstants.HORIZONTAL);
		separator.setMaximumSize(new Dimension(SEPARATOR_WIDTH, SEPARATOR_HEIGHT));
		
		// link components
		CBLinkLabel signUpLabel = new CBLinkLabel(CBStrings.SIGN_UP.toString(), new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (listener != null) {
					listener.eventReceived(new CBEvent(this, Events.REQ_SIGNUP_PAGE));
				}
			}
		});
		
		CBLinkLabel forGotLabel = new CBLinkLabel(CBStrings.FOR_GOT_PASSWORD.toString(), new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (listener != null) {
					listener.eventReceived(new CBEvent(this, Events.REQ_ACCOUNT_CONFIRMATION_PAGE));
				}
			}
		});
		
		JLabel orLabel = new JLabel("or");
		
		// set layout
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(logoLabel)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(emailLabel)
								.addComponent(emailField))
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(passwordLabel)
								.addComponent(passwordField))
						.addComponent(signInButton)
						.addComponent(separator)
						.addGroup(layout.createSequentialGroup()
								.addComponent(signUpLabel)
								.addComponent(orLabel)
								.addComponent(forGotLabel)))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(logoLabel)
				.addGroup(layout.createSequentialGroup()
						.addComponent(emailLabel)
						.addComponent(emailField))
				.addGroup(layout.createSequentialGroup()
						.addComponent(passwordLabel)
						.addComponent(passwordField))
				.addComponent(signInButton)
				.addComponent(separator)
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(signUpLabel)
						.addComponent(orLabel)
						.addComponent(forGotLabel))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);
	}

	@Override
	public void addEventListener(CBEventListener listener) {
		this.listener = listener;
	}

	@Override
	public void removeEventListener(CBEventListener listener) {
		this.listener = null;
	}
}
