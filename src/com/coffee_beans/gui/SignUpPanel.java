package com.coffee_beans.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.coffee_beans.common.CBStrings;

public class SignUpPanel extends JPanel {
	private static final int TEXTFIELD_WIDTH = 200;
	private static final int TEXTFIELD_HEIGHT = 20;
	
	private static SignUpPanel uniqueInstance;
	
	private JTextField nameField;
	private JTextField emailField;
	private JTextField createPasswordField;
	private JTextField confirmPasswordField;
	
	private JLabel emailWarningLabel;
	private JLabel passwordWarningLabel;
	
	private SignUpPanel() {
		buildGui();
	}

	public static synchronized SignUpPanel getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new SignUpPanel();
		
		return uniqueInstance;
	}
	
	private void buildGui() {
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
		JLabel upperLabel = new JLabel(CBStrings.NOTICE_SIGN_UP.toString());
		upperLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		CBLinkLabel termsLabel = new CBLinkLabel(CBStrings.TERMS_OF_SERVICE.toString(), new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Clicked 'Terms of service'");
			}
		});
		termsLabel.setAlignmentX(LEFT_ALIGNMENT);
		
		CBLinkLabel policyLabel = new CBLinkLabel(CBStrings.PRIVACY_POLICY.toString(), new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Clicked 'Privacy policy'");
			}
		});
		policyLabel.setAlignmentX(RIGHT_ALIGNMENT);
		
		JLabel andLabel = new JLabel("and");
		andLabel.setAlignmentX(CENTER_ALIGNMENT);
					
		GridBagLayout noticeLayout = new GridBagLayout();			
		GridBagConstraints layoutConstraints = new GridBagConstraints();
		layoutConstraints.anchor = GridBagConstraints.CENTER;
		layoutConstraints.insets = new Insets(1,3,1,3);
		
		JPanel noticePanel = new JPanel();
		noticePanel.setAlignmentX(CENTER_ALIGNMENT);
		noticePanel.setLayout(noticeLayout);
		
		layoutConstraints.gridwidth = 3;
		addToGrid(noticePanel, upperLabel,	layoutConstraints, 0, 0);
		
		layoutConstraints.gridwidth = 1;
		addToGrid(noticePanel, termsLabel,	layoutConstraints, 1, 0);
		addToGrid(noticePanel, andLabel,	layoutConstraints, 1, 1);
		addToGrid(noticePanel, policyLabel,	layoutConstraints, 1, 2);
		
		// sign up button
		JButton signUpButton = new JButton(CBStrings.SIGN_UP.toString());
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