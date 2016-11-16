package com.coffee_beans.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SignInPanel extends JPanel {
	private static final int GAP_VERTICAL = 20;
	
	private static final int LOGO_WIDTH = 200;
	private static final int LOGO_HEIGHT = 100;

	private static final int TEXTFIELD_WIDTH = 200;
	private static final int TEXTFIELD_HEIGHT = 20;
	
	private static final int DIVIDER_LINE_WIDTH = 400;
	private static final int DIVIDER_LINE_HEIGHT = 20;
	
	private JTextField emailField;
	private JTextField passwordField;
	
	public SignInPanel() {
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		
		setLayout(layout);
		setBorder(new EmptyBorder(50,0,0,50));
		
		// logo label
		JLabel logoLabel = null;
		try {
			Image logoImg = ImageIO.read(new File("images/Logo.png"))
								.getScaledInstance(LOGO_WIDTH, LOGO_HEIGHT, Image.SCALE_SMOOTH);
			
			logoLabel = new JLabel(new ImageIcon(logoImg));
			logoLabel.setAlignmentX(CENTER_ALIGNMENT);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// email components
		JLabel emailLabel = new JLabel("Email");
		emailLabel.setAlignmentX(LEFT_ALIGNMENT);
		
		emailField = new JTextField();
		emailField.setMaximumSize(new Dimension(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT));
		emailField.setAlignmentX(LEFT_ALIGNMENT);
		
		JPanel emailPanel = new JPanel();
		BoxLayout emailLayout = new BoxLayout(emailPanel, BoxLayout.Y_AXIS);
		emailPanel.setLayout(emailLayout);
		emailPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		emailPanel.add(emailLabel);
		emailPanel.add(emailField);
		
		// password components
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setAlignmentX(LEFT_ALIGNMENT);

		passwordField = new JTextField();
		passwordField.setMaximumSize(new Dimension(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT));
		passwordField.setAlignmentX(LEFT_ALIGNMENT);
		
		JPanel passwordPanel = new JPanel();
		BoxLayout passwordLayout = new BoxLayout(passwordPanel, BoxLayout.Y_AXIS);
		passwordPanel.setLayout(passwordLayout);
		passwordPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordField);
		
		// sign-in button
		JButton signInButton = new JButton("Sing In");
		signInButton.setAlignmentX(CENTER_ALIGNMENT);
		
		SignInClickedListner signInClickedListner = new SignInClickedListner();
		signInButton.addActionListener(signInClickedListner);
		
		// divider line
		JLabel lineLabel = null;
		try {
			Image lineImg = ImageIO.read(new File("images/Line_Divider.png"))
								.getScaledInstance(DIVIDER_LINE_WIDTH, DIVIDER_LINE_HEIGHT, Image.SCALE_SMOOTH);
			
			lineLabel = new JLabel(new ImageIcon(lineImg));
			lineLabel.setAlignmentX(CENTER_ALIGNMENT);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// link components
		JPanel linkPanel = new JPanel();
		linkPanel.setAlignmentX(CENTER_ALIGNMENT);
		try {
			LinkLabel signUpLabel = new LinkLabel("SignUp", new URI("http://www.naver.com"));
			linkPanel.add(signUpLabel);
			
			linkPanel.add(new JLabel("or"));
			
			LinkLabel forGotPasswordLabel = new LinkLabel("For get password", new URI("http://www.naver.com"));
			linkPanel.add(forGotPasswordLabel);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		// add components on the Sign-in panel.
		add(logoLabel);
		addVerticalGap(GAP_VERTICAL);
		add(emailPanel);
		addVerticalGap(GAP_VERTICAL);
		add(passwordPanel);
		addVerticalGap(GAP_VERTICAL);
		add(signInButton);
		addVerticalGap(GAP_VERTICAL);
		add(lineLabel);
		addVerticalGap(GAP_VERTICAL);
		add(linkPanel);
		
		setVisible(true);
	}
	
	private void addVerticalGap(int gap) {
		add(Box.createRigidArea(new Dimension(0, gap)));
	}
}

class SignInClickedListner implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// get character strings from email and password fields.
	}
}
