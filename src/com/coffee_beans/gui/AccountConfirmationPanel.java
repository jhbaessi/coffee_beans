package com.coffee_beans.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

import com.coffee_beans.common.Account;
import com.coffee_beans.common.CBStrings;
import com.coffee_beans.gui.WarningLabel.WarningStrings;
import com.coffee_beans.util.CBEvent;
import com.coffee_beans.util.CBEvent.Events;
import com.coffee_beans.util.CBEventListener;
import com.coffee_beans.util.CBEventSource;
import com.coffee_beans.util.CBSerializer;
import com.coffee_beans.util.EmailAddressFormChecker;

public class AccountConfirmationPanel extends JPanel implements CBEventSource {
	private static final int BUTTON_ICON_WIDTH	= 30;
	private static final int BUTTON_ICON_HEIGHT	= 30;
	
	private static final int TEXTFIELD_WIDTH	= 300;
	private static final int TEXTFIELD_HEIGHT	= 30;	
	
	private static AccountConfirmationPanel uniqueInstance;
	
	private WarningLabel warningLabel;
	
	private CBEventListener listener;
	
	public static synchronized AccountConfirmationPanel getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new AccountConfirmationPanel();
		
		return uniqueInstance; 
	}
	
	private AccountConfirmationPanel() {
		buildGui();
	}
	
	private void buildGui() {
		// previous page button
		JButton prevPageButton = null;
		try {
			BufferedImage prevPageImg = ImageIO.read(new File("images/Prev_Page.png"));
			
			prevPageButton = new JButton(new ImageIcon(prevPageImg.getScaledInstance(BUTTON_ICON_WIDTH, BUTTON_ICON_HEIGHT, Image.SCALE_SMOOTH)));
			prevPageButton.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			prevPageButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (listener != null) {
						listener.eventReceived(new CBEvent(this, Events.REQ_SIGNIN_PAGE));
					}
				}
			});
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// user name components
		JLabel usernameLabel = new JLabel(CBStrings.USERNAME.toString());
		
		JTextField usernameField = new JTextField();
		usernameField.setMaximumSize(new Dimension(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT));
		
		// email field
		JLabel emailLabel = new JLabel(CBStrings.EMAIL.toString());
		
		JTextField emailField = new JTextField();
		emailField.setMaximumSize(new Dimension(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT));
		
		// warning message
		warningLabel = new WarningLabel();
		
		// request button
		JButton requestButton = new JButton(CBStrings.REQUEST_ACCOUNT_VERIFICATION.toString());
		requestButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				WarningStrings warning = WarningStrings.NO_WARNING;
				
				String username = usernameField.getText();
				String email = emailField.getText();
				
				if (username.isEmpty()) {
					warning = WarningStrings.ENTER_USERNAME;
				} else if (email.isEmpty()) {
					warning = WarningStrings.ENTER_EMAIL_ADDRESS;
				} else {
					EmailAddressFormChecker checker = new EmailAddressFormChecker(email);
					if (checker.isValid()) {
						if (listener != null) {
							byte[] bytes = CBSerializer.serialize(new Account(username, email, null));
							if (bytes != null) {
								listener.eventReceived(new CBEvent(this, Events.REQ_VERIFYING_USERINFO, bytes));
							}
						}
					} else {
						warning = WarningStrings.INVALID_EMAIL_FORM;
					}
				}
				
				warningLabel.setWarning(warning);
			}
		});
		
		// set layout
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addComponent(prevPageButton)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(usernameLabel)
								.addComponent(usernameField))
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(emailLabel)
								.addComponent(emailField))
						.addComponent(warningLabel)
						.addComponent(requestButton))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, prevPageButton.getPreferredSize().width, prevPageButton.getMaximumSize().width)
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(prevPageButton)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(layout.createSequentialGroup()
						.addComponent(usernameLabel)
						.addComponent(usernameField))
				.addGroup(layout.createSequentialGroup()
						.addComponent(emailLabel)
						.addComponent(emailField))
				.addComponent(warningLabel)
				.addComponent(requestButton)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, prevPageButton.getPreferredSize().height, prevPageButton.getMaximumSize().height)
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
