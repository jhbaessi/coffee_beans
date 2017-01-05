package com.coffee_beans.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import com.coffee_beans.common.Account;
import com.coffee_beans.common.CBStrings;
import com.coffee_beans.gui.WarningLabel.WarningStrings;
import com.coffee_beans.util.CBEvent;
import com.coffee_beans.util.CBEvent.Events;
import com.coffee_beans.util.CBEventListener;
import com.coffee_beans.util.CBEventSource;
import com.coffee_beans.util.CBSerializer;

public class PasswordUpdatePanel extends JPanel implements CBEventSource {
	private static final int BUTTON_ICON_WIDTH	= 30;
	private static final int BUTTON_ICON_HEIGHT	= 30;
	
	private static final int TEXTFIELD_WIDTH	= 300;
	private static final int TEXTFIELD_HEIGHT	= 30;	
	
	private static PasswordUpdatePanel uniqueInstance;
	
	private WarningLabel warningLabel;
	
	private CBEventListener listener;
	
	public static synchronized PasswordUpdatePanel getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new PasswordUpdatePanel();
		
		return uniqueInstance; 
	}
	
	private PasswordUpdatePanel() {
		buildGui();
	}
	
	private void buildGui() {		
		// new password components
		JLabel newPasswordLabel = new JLabel(CBStrings.NEW_PASSWORD.toString());
		newPasswordLabel.setAlignmentX(LEFT_ALIGNMENT);
		
		JPasswordField newPasswordField = new JPasswordField();
		newPasswordField.setMaximumSize(new Dimension(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT));
		newPasswordField.setAlignmentX(LEFT_ALIGNMENT);
		
		JPanel newPasswordPanel = new JPanel();
		newPasswordPanel.setLayout(new BoxLayout(newPasswordPanel, BoxLayout.Y_AXIS));
		newPasswordPanel.setAlignmentX(CENTER_ALIGNMENT);
		newPasswordPanel.add(newPasswordLabel);
		newPasswordPanel.add(newPasswordField);
		
		// confirm password components
		JLabel confirmPasswordLabel = new JLabel(CBStrings.CONFIRM_PASSWORD.toString());
		confirmPasswordLabel.setAlignmentX(LEFT_ALIGNMENT);
		
		JPasswordField confirmPasswordField = new JPasswordField();
		confirmPasswordField.setMaximumSize(new Dimension(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT));
		confirmPasswordField.setAlignmentX(LEFT_ALIGNMENT);
		
		JPanel confirmPasswordPanel = new JPanel();
		confirmPasswordPanel.setLayout(new BoxLayout(confirmPasswordPanel, BoxLayout.Y_AXIS));
		confirmPasswordPanel.setAlignmentX(CENTER_ALIGNMENT);
		confirmPasswordPanel.add(confirmPasswordLabel);
		confirmPasswordPanel.add(confirmPasswordField);

		// warning message
		warningLabel = new WarningLabel(WarningStrings.NO_WARNING);
		warningLabel.setPreferredSize(new Dimension(700, 30));
		warningLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		// update button
		JButton updatePasswordButton = new JButton(CBStrings.UPDATE_PASSWORD.toString());
		updatePasswordButton.setAlignmentX(CENTER_ALIGNMENT);
		updatePasswordButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				WarningStrings warning = WarningStrings.NO_WARNING;
				
				String newPassword = String.valueOf(newPasswordField.getPassword());
				String confirmPassword = String.valueOf(confirmPasswordField.getPassword());
				
				if (newPassword.isEmpty()) {
					warning = WarningStrings.ENTER_NEW_PASSWORD;
				} else if (confirmPassword.isEmpty()) {
					warning = WarningStrings.ENTER_CONFIRM_PASSWORD;
				} else {
					if (newPassword.compareTo(confirmPassword) == 0) {
						if (listener != null) {
							byte[] bytes = CBSerializer.serialize(new Account(null, null, newPassword));
							if (bytes != null) {
								listener.eventReceived(new CBEvent(this, Events.REQ_UPDATE_PASSWORD, bytes));
							}
						}
					} else {
						warning = WarningStrings.NOT_MATCH_PASSWORD;
					}
				}
				
				warningLabel.setWarning(warning);
			}
		});
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.setBorder(new EmptyBorder(50, 100, 50, 100));
		centerPanel.add(newPasswordPanel);
		centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		centerPanel.add(confirmPasswordPanel);
		centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		centerPanel.add(warningLabel);
		centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		centerPanel.add(updatePasswordButton);
        
		setLayout(new BorderLayout());
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
