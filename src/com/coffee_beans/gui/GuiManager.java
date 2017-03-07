package com.coffee_beans.gui;

import com.coffee_beans.util.CBEvent;
import com.coffee_beans.util.CBEventListener;

public class GuiManager implements CBEventListener {
	public GuiManager() {
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MessengerFrame.getInstance().setVisible(true);
            }
        });
	}

	@Override
	public void eventReceived(CBEvent event) {
		
	}
}
