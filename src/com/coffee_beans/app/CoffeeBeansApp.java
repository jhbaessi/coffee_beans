package com.coffee_beans.app;

import javax.swing.JFrame;

import com.coffee_beans.gui.SignInPanel;

public class CoffeeBeansApp {
	public static void main(String [] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(960,540);	// 1024x576, 960x540, 864,486
		
		frame.setVisible(true);
	}
}
