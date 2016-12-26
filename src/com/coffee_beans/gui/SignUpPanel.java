package com.coffee_beans.gui;

import java.awt.Component;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.coffee_beans.common.NewAccount;
import com.coffee_beans.common.CBStrings;
import com.coffee_beans.util.CBEvent;
import com.coffee_beans.util.CBEventListener;
import com.coffee_beans.util.CBEventSource;
import com.coffee_beans.util.CBSerializer;
import com.coffee_beans.util.CBEvent.Events;

public class SignUpPanel extends JPanel implements CBEventSource {
	private static final int TEXTFIELD_WIDTH = 200;
	private static final int TEXTFIELD_HEIGHT = 20;
	
	private static SignUpPanel uniqueInstance;
	
	private JTextField nameField;
	private JTextField emailField;
	private JTextField createPasswordField;
	private JTextField confirmPasswordField;
	
	private JLabel emailWarningLabel;
	private JLabel passwordWarningLabel;
	
	private CBEventListener listener;
	
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
		
		// sign up button
		JButton backButton = new JButton(new ImageIcon(((new ImageIcon("images/Back_Button.png").getImage().getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH)))));
		backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (listener != null) {
					listener.eventReceived(new CBEvent(this, Events.REQ_MAIN_PAGE));
				}
			}
		});
				
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
				try {
					openWebpage(new URI("https://github.com/jhbaessi/coffee_beans"));
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("Clicked 'Terms of service'");
			}
		});
		termsLabel.setAlignmentX(LEFT_ALIGNMENT);
		
		CBLinkLabel policyLabel = new CBLinkLabel(CBStrings.PRIVACY_POLICY.toString(), new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					openWebpage(new URI("https://github.com/jhbaessi/coffee_beans"));
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
		
		signUpButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String name = nameField.getText();
				String email = emailField.getText();
				String createPassword = createPasswordField.getText();
				String confirmPassword = confirmPasswordField.getText();
				
				if (name.isEmpty()) {
					
				} else if (email.isEmpty()){
					
				} else if (createPassword.isEmpty()){
					
				} else if (confirmPassword.isEmpty()){
					
				} else {
					if (listener != null) {
						byte[] bytes = CBSerializer.serialize(new NewAccount(name, email, createPassword, confirmPassword));
						if (bytes != null) {
							listener.eventReceived(new CBEvent(this, Events.REQ_NEW_ACCOUNT, bytes));
						} else {
							// failed to serialized
						}
					}
				}
			}
		});
		
		SignUpClickedListner signUpClickedListner = new SignUpClickedListner();
		signUpButton.addActionListener(signUpClickedListner);
		
		// add components on the Sign-up panel
		add(backButton);
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

	@Override
	public void addEventListener(CBEventListener listener) {
		this.listener = listener;		
	}

	@Override
	public void removeEventListener(CBEventListener listener) {
		this.listener = null;
	}
	
	private void openWebpage(URI uri) {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(uri);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
}

class SignUpClickedListner implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// get character strings from text fields.
		
	}
}