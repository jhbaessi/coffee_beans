package com.coffee_beans.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.sun.xml.internal.ws.message.EmptyMessageImpl;
import com.coffee_beans.common.CBStrings;

public class SignInPanel extends JPanel {
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
		
	private SignInPanel() {
		buildGui();
	}
	
	public static synchronized SignInPanel getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new SignInPanel();
			
		return uniqueInstance;
	}
	
	private void buildGui() {
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
		JLabel emailLabel = new JLabel(CBStrings.EMAIL.toString());
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
		JLabel passwordLabel = new JLabel(CBStrings.PASSWORD.toString());
		passwordLabel.setAlignmentX(LEFT_ALIGNMENT);

		passwordField = new JPasswordField();
		passwordField.setMaximumSize(new Dimension(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT));
		passwordField.setAlignmentX(LEFT_ALIGNMENT);
		
		JPanel passwordPanel = new JPanel();
		BoxLayout passwordLayout = new BoxLayout(passwordPanel, BoxLayout.Y_AXIS);
		passwordPanel.setLayout(passwordLayout);
		passwordPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordField);
		
		// sign-in button
		JButton signInButton = new JButton(CBStrings.SIGN_IN.toString());
		signInButton.setAlignmentX(CENTER_ALIGNMENT);
		
		signInButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Email: " + emailField.getText());
				System.out.println("Password: " + String.valueOf(passwordField.getPassword()));
			}
		});
		
		// divider line
		CBSeparator separator = new CBSeparator(SwingConstants.HORIZONTAL);
		separator.setMaximumSize(new Dimension(SEPARATOR_WIDTH, SEPARATOR_HEIGHT));
		separator.setAlignmentX(CENTER_ALIGNMENT);
		
		// link components
		CBLinkLabel signUpLabel = new CBLinkLabel(CBStrings.SIGN_UP.toString(), new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Clicked Sign-up");
			}
		});
		
		CBLinkLabel forGotLabel = new CBLinkLabel(CBStrings.FOR_GOT_PASSWORD.toString(), new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Clicked For got password");
			}
		});
		
		JPanel linkPanel = new JPanel();
		linkPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		linkPanel.add(signUpLabel);
		linkPanel.add(new JLabel("or"));
		linkPanel.add(forGotLabel);
		
		// add components on the Sign-in panel.
		add(logoLabel);
		addVerticalGap(GAP_VERTICAL);
		add(emailPanel);
		addVerticalGap(GAP_VERTICAL);
		add(passwordPanel);
		addVerticalGap(GAP_VERTICAL);
		add(signInButton);
		addVerticalGap(GAP_VERTICAL);
		add(separator);
		addVerticalGap(GAP_VERTICAL);
		add(linkPanel);
		
		setVisible(true);
	}
	
	private void addVerticalGap(int gap) {
		add(Box.createRigidArea(new Dimension(0, gap)));
	}
}
