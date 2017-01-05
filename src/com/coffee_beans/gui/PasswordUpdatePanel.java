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

import com.coffee_beans.common.CBStrings;
import com.coffee_beans.gui.WarningLabel.WarningStrings;
import com.coffee_beans.util.CBEventListener;
import com.coffee_beans.util.CBEventSource;

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
		
		JTextField newPasswordField = new JTextField();
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
		
		JTextField confirmPasswordField = new JTextField();
		confirmPasswordField.setMaximumSize(new Dimension(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT));
		confirmPasswordField.setAlignmentX(LEFT_ALIGNMENT);
		
		JPanel confirmPasswordPanel = new JPanel();
		confirmPasswordPanel.setLayout(new BoxLayout(confirmPasswordPanel, BoxLayout.Y_AXIS));
		confirmPasswordPanel.setAlignmentX(CENTER_ALIGNMENT);
		confirmPasswordPanel.add(confirmPasswordLabel);
		confirmPasswordPanel.add(confirmPasswordField);

		// warning message
		warningLabel = new WarningLabel(WarningStrings.NOT_MATCH_PASSWORD);
		warningLabel.setPreferredSize(new Dimension(700, 30));
		warningLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		// update button
		JButton updatePasswordButton = new JButton(CBStrings.UPDATE_PASSWORD.toString());
		updatePasswordButton.setAlignmentX(CENTER_ALIGNMENT);
		updatePasswordButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (warningLabel.getWarning() == WarningStrings.NO_WARNING) {
					warningLabel.setWarning(WarningStrings.NOT_MATCH_PASSWORD);
				} else {
					warningLabel.setWarning(WarningStrings.NO_WARNING);
				}
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
