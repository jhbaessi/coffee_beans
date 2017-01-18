package com.coffee_beans.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URI;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
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
import com.coffee_beans.util.HtmlLoader;

public class SignUpPanel extends JPanel implements CBEventSource {
	private static final int TEXTFIELD_WIDTH = 200;
	private static final int TEXTFIELD_HEIGHT = 20;
	
	private static SignUpPanel uniqueInstance;
	
	private JTextField nameField;
	private JTextField emailField;
	private JTextField createPasswordField;
	private JTextField confirmPasswordField;
	
	private WarningLabel warningLabel;
	
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
		BorderLayout layout = new BorderLayout();
		
		setLayout(layout);
		setBorder(new EmptyBorder(50,0,50,0));
		
		// back button
		JButton backButton = new JButton(new ImageIcon(((new ImageIcon("images/Back_Button.png").getImage().getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH)))));		
		backButton.setBorderPainted(false);
		backButton.setBorder(null);
		backButton.setContentAreaFilled(false);
		backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (listener != null) {
					nameField.setText(null);
					emailField.setText(null);
					createPasswordField.setText(null);
					confirmPasswordField.setText(null);
					listener.eventReceived(new CBEvent(this, Events.REQ_MAIN_PAGE));
				}
			}
		});
		JPanel backPanel = new JPanel();
		BoxLayout backLayout = new BoxLayout(backPanel, BoxLayout.Y_AXIS);
		backPanel.setLayout(backLayout);
		backPanel.setAlignmentX(CENTER_ALIGNMENT);
		backPanel.setBorder(new EmptyBorder(0, 50, 0, 0));
		backPanel.add(backButton);
		
		JPanel centerPanel = new JPanel();
		BoxLayout centerLayout = new BoxLayout(centerPanel, BoxLayout.Y_AXIS);
		centerPanel.setLayout(centerLayout);
		centerPanel.setAlignmentX(CENTER_ALIGNMENT);
		
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

		JPanel emailPanel = new JPanel();
		BoxLayout emailLayout = new BoxLayout(emailPanel, BoxLayout.Y_AXIS);
		emailPanel.setLayout(emailLayout);
		emailPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		emailPanel.add(emailLabel);
		emailPanel.add(emailField);
		
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
		
		warningLabel = new WarningLabel();
		
		JPanel passwordPanel = new JPanel();
		BoxLayout passwordLayout = new BoxLayout(passwordPanel, BoxLayout.Y_AXIS);
		passwordPanel.setLayout(passwordLayout);
		passwordPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		passwordPanel.add(createPasswordLabel);
		passwordPanel.add(createPasswordField);
		passwordPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		passwordPanel.add(confirmPasswordLabel);
		passwordPanel.add(confirmPasswordField);		
		passwordPanel.add(warningLabel);
				
		// notice labels
		JLabel upperLabel = new JLabel(CBStrings.NOTICE_SIGN_UP.toString());
		upperLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		CBLinkLabel termsLabel = new CBLinkLabel(CBStrings.TERMS_OF_SERVICE.toString(), new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String dir = System.getProperty("user.dir") + "/docs/terms_of_service.html";
				HtmlLoader.loadHtml(new File(dir));
			}
		});
		termsLabel.setAlignmentX(LEFT_ALIGNMENT);
		
		CBLinkLabel policyLabel = new CBLinkLabel(CBStrings.PRIVACY_POLICY.toString(), new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String dir = System.getProperty("user.dir") + "/docs/privacy_policy.html";
				HtmlLoader.loadHtml(new File(dir));
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
				String password = null;
				
				if (name.isEmpty()) {
					warningLabel.setWarning(WarningStrings.ENTER_USERNAME);
				} else if (email.isEmpty()){
					warningLabel.setWarning(WarningStrings.ENTER_EMAIL_ADDRESS);
				} else if (createPassword.isEmpty()){
					warningLabel.setWarning(WarningStrings.ENTER_PASSWORD);
				} else if (confirmPassword.isEmpty()){
					warningLabel.setWarning(WarningStrings.ENTER_CONFIRM_PASSWORD);
				} else if (!createPassword.equals(confirmPassword)){
					warningLabel.setWarning(WarningStrings.NOT_MATCH_PASSWORD);
				} else {
					warningLabel.setWarning(WarningStrings.NO_WARNING);
					if (listener != null) {
						password = createPassword;
						byte[] bytes = CBSerializer.serialize(new Account(name, email, password));
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
		add(backPanel, BorderLayout.NORTH);
		centerPanel.add(namePanel);
		centerPanel.add(emailPanel);
		centerPanel.add(passwordPanel);
		if (noticePanel != null)
			centerPanel.add(noticePanel);
		
		centerPanel.add(signUpButton);
		add(centerPanel, BorderLayout.CENTER);
		
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