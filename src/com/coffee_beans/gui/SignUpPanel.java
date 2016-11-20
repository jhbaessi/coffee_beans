package com.coffee_beans.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SignUpPanel extends JPanel {
	private static final int TEXTFIELD_WIDTH = 200;
	private static final int TEXTFIELD_HEIGHT = 20;
	
	private JTextField nameField;
	private JTextField emailField;
	private JTextField createPasswordField;
	private JTextField confirmPasswordField;
	
	private JLabel emailWarningLabel;
	private JLabel passwordWarningLabel;
	
	public SignUpPanel() {
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		
		setLayout(layout);
		setBorder(new EmptyBorder(50,0,50,0));
		
		// user name components
		JLabel nameLabel = new JLabel("User name");
		nameLabel.setAlignmentX(LEFT_ALIGNMENT);
		
		nameField = new JTextField();
		nameField.setMaximumSize(new Dimension(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT));
		nameField.setAlignmentX(LEFT_ALIGNMENT);
		
		JPanel namePanel = new JPanel();
		BoxLayout nameLayout = new BoxLayout(namePanel, BoxLayout.Y_AXIS);
		namePanel.setLayout(nameLayout);
		namePanel.setAlignmentX(CENTER_ALIGNMENT);
		
		namePanel.add(nameLabel);
		namePanel.add(nameField);
		
		// email components
		JLabel emailLabel = new JLabel("Email");
		emailLabel.setAlignmentX(LEFT_ALIGNMENT);
		
		emailField = new JTextField();
		emailField.setMaximumSize(new Dimension(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT));
		emailField.setAlignmentX(LEFT_ALIGNMENT);
		
		emailWarningLabel = new JLabel("Email is invalid or already taken.");
		emailWarningLabel.setAlignmentX(LEFT_ALIGNMENT);
//		emailWarningLabel.setVisible(false);
		emailWarningLabel.setOpaque(true);
		emailWarningLabel.setForeground(Color.RED);

		JPanel emailPanel = new JPanel();
		BoxLayout emailLayout = new BoxLayout(emailPanel, BoxLayout.Y_AXIS);
		emailPanel.setLayout(emailLayout);
		emailPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		emailPanel.add(emailLabel);
		emailPanel.add(emailField);
		emailPanel.add(emailWarningLabel);
		
		// password components
		JLabel createPasswordLabel = new JLabel("Create a password");
		createPasswordLabel.setAlignmentX(LEFT_ALIGNMENT);
		
		createPasswordField = new JTextField();
		createPasswordField.setMaximumSize(new Dimension(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT));
		createPasswordField.setAlignmentX(LEFT_ALIGNMENT);
		
		JLabel confirmPasswordLabel = new JLabel("Confirm your password");
		confirmPasswordLabel.setAlignmentX(LEFT_ALIGNMENT);
		
		confirmPasswordField = new JTextField();
		confirmPasswordField.setMaximumSize(new Dimension(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT));
		confirmPasswordField.setAlignmentX(LEFT_ALIGNMENT);
		
		passwordWarningLabel = new JLabel("These password don't match.");
		passwordWarningLabel.setAlignmentX(LEFT_ALIGNMENT);
		passwordWarningLabel.setOpaque(true);
		passwordWarningLabel.setForeground(Color.RED);
//		passwordWarningLabel.setVisible(false);
		
		JPanel passwordPanel = new JPanel();
		BoxLayout passwordLayout = new BoxLayout(passwordPanel, BoxLayout.Y_AXIS);
		passwordPanel.setLayout(passwordLayout);
		passwordPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		passwordPanel.add(createPasswordLabel);
		passwordPanel.add(createPasswordField);
		passwordPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		passwordPanel.add(confirmPasswordLabel);
		passwordPanel.add(confirmPasswordField);		
		passwordPanel.add(passwordWarningLabel);
				
		// notice labels
		JPanel noticePanel = null;
		try {
			JLabel upperLabel = new JLabel("By clicking \"Sign Up\", you agree to our");
			upperLabel.setAlignmentX(CENTER_ALIGNMENT);
			
			LinkLabel termsLabel = new LinkLabel("terms of service", new URI("http://www.naver.com"));
			termsLabel.setAlignmentX(CENTER_ALIGNMENT);
			
			JLabel andLabel = new JLabel("and");
			andLabel.setAlignmentX(CENTER_ALIGNMENT);
						
			LinkLabel policyLabel = new LinkLabel("privacy policy", new URI("http://www.naver.com"));
			policyLabel.setAlignmentX(CENTER_ALIGNMENT);
			
			GridBagLayout noticeLayout = new GridBagLayout();			
			GridBagConstraints layoutConstraints = new GridBagConstraints();
			layoutConstraints.anchor = GridBagConstraints.CENTER;
			layoutConstraints.insets = new Insets(1,3,1,3);
			
			noticePanel = new JPanel();
			noticePanel.setAlignmentX(CENTER_ALIGNMENT);
			noticePanel.setLayout(noticeLayout);
			
			layoutConstraints.gridwidth = 3;
			addToGrid(noticePanel, upperLabel,	layoutConstraints, 0, 0);
			
			layoutConstraints.gridwidth = 1;
			addToGrid(noticePanel, termsLabel,	layoutConstraints, 1, 0);
			addToGrid(noticePanel, andLabel,	layoutConstraints, 1, 1);
			addToGrid(noticePanel, policyLabel,	layoutConstraints, 1, 2);
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		// sign up button
		JButton signUpButton = new JButton("Sign Up");
		signUpButton.setAlignmentX(CENTER_ALIGNMENT);
		
		SignUpClickedListner signUpClickedListner = new SignUpClickedListner();
		signUpButton.addActionListener(signUpClickedListner);
		
		// add components on the Sign-up panel
		add(namePanel);
		add(emailPanel);
		add(passwordPanel);
		if (noticePanel != null)
			add(noticePanel);
		
		add(signUpButton);
		
		setVisible(true);
	}

	private void addToGrid(JComponent parent, JComponent component, GridBagConstraints constraints, int x, int y) {
		constraints.gridx = x;
		constraints.gridx = y;
		
		parent.add(component, constraints);
	}
}

class SignUpClickedListner implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// get character strings from text fields.
	}
}