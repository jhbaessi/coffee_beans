package com.coffee_beans.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

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
			prevPageButton.setAlignmentX(LEFT_ALIGNMENT);
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
		usernameLabel.setAlignmentX(LEFT_ALIGNMENT);
		
		JTextField usernameField = new JTextField();
		usernameField.setMaximumSize(new Dimension(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT));
		usernameField.setAlignmentX(LEFT_ALIGNMENT);
		
		JPanel usernamePanel = new JPanel();
		usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.Y_AXIS));
		usernamePanel.setAlignmentX(CENTER_ALIGNMENT);
		usernamePanel.add(usernameLabel);
		usernamePanel.add(usernameField);
		
		// email field
		JLabel emailLabel = new JLabel(CBStrings.EMAIL.toString());
		emailLabel.setAlignmentX(LEFT_ALIGNMENT);
		
		JTextField emailField = new JTextField();
		emailField.setMaximumSize(new Dimension(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT));
		emailField.setAlignmentX(LEFT_ALIGNMENT);
		
		JPanel emailPanel = new JPanel();
		emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.Y_AXIS));
		emailPanel.setAlignmentX(CENTER_ALIGNMENT);
		emailPanel.add(emailLabel);
		emailPanel.add(emailField);

		// warning message
		warningLabel = new WarningLabel();
		warningLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		// request button
		JButton requestButton = new JButton(CBStrings.REQUEST_ACCOUNT_VERIFICATION.toString());
		requestButton.setAlignmentX(CENTER_ALIGNMENT);
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
		
		// add components on the AccountConfirmationPanel
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
		northPanel.add(prevPageButton);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.setBorder(new EmptyBorder(50, 100, 50, 100));
		centerPanel.add(usernamePanel);
		centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		centerPanel.add(emailPanel);
		centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		centerPanel.add(warningLabel);
		centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		centerPanel.add(requestButton);
        
		setLayout(new BorderLayout());
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
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
