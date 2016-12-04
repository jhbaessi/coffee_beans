package com.coffee_beans.gui;

import java.awt.Dimension;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class CBSeparator extends JSeparator{
	public CBSeparator(int orientation) {
		Dimension separatorDimension = null;
		if (orientation == SwingConstants.HORIZONTAL) {
			separatorDimension = new Dimension(getMaximumSize().width,getPreferredSize().height);
		} else {
			separatorDimension = new Dimension(getPreferredSize().width,getMaximumSize().height);
		}
		
		setOrientation(orientation);
		setMaximumSize(separatorDimension);
	}
}
