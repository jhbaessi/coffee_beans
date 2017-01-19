package com.coffee_beans.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

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
import com.coffee_beans.util.HtmlLoader;

public class SignUpPanel extends JPanel implements CBEventSource {
	private static final int BUTTON_ICON_WIDTH	= 30;
	private static final int BUTTON_ICON_HEIGHT	= 30;
	
	private static final int TEXTFIELD_WIDTH = 300;
	private static final int TEXTFIELD_HEIGHT = 30;
	
	private static SignUpPanel uniqueInstance;
	
	private JTextField nameField;
	private JTextField emailField;
	private JTextField createPasswordField;
	private JTextField confirmPasswordField;
	
	private WarningLabel warningLabel;
	
	private CBEventListener listener;

	public static synchronized SignUpPanel getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new SignUpPanel();
		
		return uniqueInstance;
	}
	
	private SignUpPanel() {
		buildGui();
	}
	
	private void buildGui() {
		// back button
		JButton backButton = new JButton(new ImageIcon(((new ImageIcon("images/Back_Button.png").getImage().getScaledInstance(BUTTON_ICON_WIDTH, BUTTON_ICON_HEIGHT, java.awt.Image.SCALE_SMOOTH)))));		
		backButton.setBorderPainted(false);
		backButton.setBorder(null);
		backButton.setContentAreaFilled(false);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (listener != null) {
					nameField.setText(null);
					emailField.setText(null);
					createPasswordField.setText(null);
					confirmPasswordField.setText(null);
					warningLabel.setWarning(WarningStrings.NO_WARNING);
					
					listener.eventReceived(new CBEvent(this, Events.REQ_MAIN_PAGE));
				}
			}
		});

		// user name components
		JLabel nameLabel = new JLabel(CBStrings.USERNAME.toString());
		
		nameField = new JTextField();
		nameField.setMaximumSize(new Dimension(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT));
		
		// email components
		JLabel emailLabel = new JLabel(CBStrings.EMAIL.toString());
		
		emailField = new JTextField();
		emailField.setMaximumSize(new Dimension(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT));

		// password components
		JLabel createPasswordLabel = new JLabel(CBStrings.CREATE_PASSWORD.toString());
		
		createPasswordField = new JTextField();
		createPasswordField.setMaximumSize(new Dimension(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT));
		
		JLabel confirmPasswordLabel = new JLabel(CBStrings.CONFIRM_PASSWORD.toString());
		
		confirmPasswordField = new JTextField();
		confirmPasswordField.setMaximumSize(new Dimension(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT));
		
		warningLabel = new WarningLabel();
		
		// notice labels
		JLabel upperLabel = new JLabel(CBStrings.NOTICE_SIGN_UP.toString());
		
		CBLinkLabel termsLabel = new CBLinkLabel(CBStrings.TERMS_OF_SERVICE.toString(), new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String dir = System.getProperty("user.dir") + "/docs/terms_of_service.html";
				HtmlLoader.loadHtml(new File(dir));
			}
		});
		
		CBLinkLabel policyLabel = new CBLinkLabel(CBStrings.PRIVACY_POLICY.toString(), new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String dir = System.getProperty("user.dir") + "/docs/privacy_policy.html";
				HtmlLoader.loadHtml(new File(dir));
			}
		});
		
		JLabel andLabel = new JLabel("and");
					
		// sign up button
		JButton signUpButton = new JButton(CBStrings.SIGN_UP.toString());
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
					EmailAddressFormChecker formChecker = new EmailAddressFormChecker(email);
					if (formChecker.isValid()) {
						if (listener != null) {
							password = createPassword;
							byte[] bytes = CBSerializer.serialize(new Account(name, email, password));
							if (bytes != null) {
								listener.eventReceived(new CBEvent(this, Events.REQ_NEW_ACCOUNT, bytes));
							} else {
								// failed to serialized
							}
						}
					} else {
						warningLabel.setWarning(WarningStrings.INVALID_EMAIL_FORM);
					}
				}
			}
		});
		
		// set layout
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addComponent(backButton)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(nameLabel)
								.addComponent(nameField))
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(emailLabel)
								.addComponent(emailField))
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(createPasswordLabel)
								.addComponent(createPasswordField))
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(confirmPasswordLabel)
								.addComponent(confirmPasswordField))
						.addComponent(warningLabel)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(upperLabel)
								.addGroup(layout.createSequentialGroup()
										.addComponent(termsLabel)
										.addComponent(andLabel)
										.addComponent(policyLabel)))
						.addComponent(signUpButton))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, backButton.getPreferredSize().width, backButton.getMaximumSize().width)
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(backButton)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(layout.createSequentialGroup()
						.addComponent(nameLabel)
						.addComponent(nameField))
				.addGroup(layout.createSequentialGroup()
						.addComponent(emailLabel)
						.addComponent(emailField))
				.addGroup(layout.createSequentialGroup()
						.addComponent(createPasswordLabel)
						.addComponent(createPasswordField))
				.addGroup(layout.createSequentialGroup()
						.addComponent(confirmPasswordLabel)
						.addComponent(confirmPasswordField))
				.addComponent(warningLabel)
				.addGroup(layout.createSequentialGroup()
						.addComponent(upperLabel)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(termsLabel)
								.addComponent(andLabel)
								.addComponent(policyLabel)))
				.addComponent(signUpButton)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, backButton.getPreferredSize().height, backButton.getMaximumSize().height)
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

class SignUpClickedListner implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// get character strings from text fields.
		
	}
}