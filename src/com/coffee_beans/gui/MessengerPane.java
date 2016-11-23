package com.coffee_beans.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MessengerPane extends JSplitPane{
	private static final int PICTURE_WIDTH = 30;
	private static final int PICTURE_HEIGHT = PICTURE_WIDTH;
	
	private static final int BUTTON_ICON_WIDTH = 20;
	private static final int BUTTON_ICON_HEIGHT = BUTTON_ICON_WIDTH;
	
	public MessengerPane() {
		buildGui();
	}
	
	private void buildGui() {
		setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		setDividerSize(3);
		
		// profile picture 
		JLabel pictureLabel = new JLabel();
		pictureLabel.setPreferredSize(new Dimension(PICTURE_WIDTH, PICTURE_HEIGHT));
		try {
			Image profileImg = ImageIO.read(new File("images/Profile_Default.png"))
					.getScaledInstance(PICTURE_WIDTH, PICTURE_HEIGHT, Image.SCALE_SMOOTH);
			pictureLabel.setIcon(new ImageIcon(profileImg));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// user name label
		JLabel nameLabel = new JLabel("Username");
		
		// status message label
		JLabel statusLabel = new JLabel("Status Message");
		
		// add components to profile panel
		GridBagLayout profileLayout = new GridBagLayout();
		GridBagConstraints profileConstraint = new GridBagConstraints();
		profileConstraint.insets = new Insets(3,5,3,5);
		
		JPanel profilePanel = new JPanel();
		profilePanel.setLayout(profileLayout);
		profilePanel.setBorder(new EmptyBorder(2,10,2,10));
		
		profileConstraint.anchor = GridBagConstraints.WEST;
		addToGrid(profilePanel, nameLabel, profileConstraint, 0, 1);
		addToGrid(profilePanel, statusLabel, profileConstraint, 1, 1);
		
		profileConstraint.anchor = GridBagConstraints.CENTER;
		profileConstraint.gridheight = 2;
		addToGrid(profilePanel, pictureLabel, profileConstraint, 0, 0);
		
		profilePanel.setMaximumSize(profilePanel.getPreferredSize());

		// selector buttons(contact, chat and menu)
		JButton contactButton = new JButton();
		JButton chatButton = new JButton();
		JButton menuButton = new JButton();
		
		try {
			Image contactImg = ImageIO.read(new File("images/Contacts.png"))
								.getScaledInstance(BUTTON_ICON_WIDTH, BUTTON_ICON_HEIGHT, Image.SCALE_SMOOTH);
			Image chatImg = ImageIO.read(new File("images/Chat.png"))
								.getScaledInstance(BUTTON_ICON_WIDTH, BUTTON_ICON_HEIGHT, Image.SCALE_SMOOTH);
			Image menuImg = ImageIO.read(new File("images/Menu.png"))
					.getScaledInstance(BUTTON_ICON_WIDTH, BUTTON_ICON_HEIGHT, Image.SCALE_SMOOTH);
			
			contactButton.setIcon(new ImageIcon(contactImg));
			chatButton.setIcon(new ImageIcon(chatImg));
			menuButton.setIcon(new ImageIcon(menuImg));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Box selectorBox = Box.createHorizontalBox();
		selectorBox.add(contactButton);		
		selectorBox.add(chatButton);
		selectorBox.add(menuButton);
		
		// function panel
		JPanel functionPanel = new JPanel();
		functionPanel.setOpaque(true);	// for test
		functionPanel.setBackground(Color.YELLOW);	// for test
		
		// add components to left side
		Box leftBox = Box.createVerticalBox();
		leftBox.add(profilePanel);
		leftBox.add(new CBSeparator(SwingConstants.HORIZONTAL));
		leftBox.add(selectorBox);
		leftBox.add(new CBSeparator(SwingConstants.HORIZONTAL));
		leftBox.add(functionPanel);
		
		// add components to right side

		setLeftComponent(leftBox);
	}
	
	private void addToGrid(JComponent parent, JComponent component, GridBagConstraints constraints, int x, int y) {
		constraints.gridx = x;
		constraints.gridx = y;
		
		parent.add(component, constraints);
	}
}
