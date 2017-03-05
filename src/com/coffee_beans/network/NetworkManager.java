package com.coffee_beans.network;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.coffee_beans.util.CBEvent;
import com.coffee_beans.util.CBEventListener;
import com.coffee_beans.util.CBEventSource;

import Action.FSMAction;
import FSM.FSM;

public class NetworkManager implements CBEventListener, CBEventSource {
	private final String STATE_CONFIG_PATH = "/resources/network_state_config.xml"; 
	
	private FSM fsm;
	
	private CBEventListener listener;
	
	public NetworkManager() {
		String configPath = System.getProperty("user.dir") + STATE_CONFIG_PATH; 
		InputStream stateStream = null;
		try {
			stateStream = new FileInputStream(configPath);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		try {
			// load state from a configuration file
			fsm = new FSM(stateStream, new FSMAction() {
				
				@Override
				public boolean action(String curState, String message, String nextState, Object args) {
					// action handling
					// ...
					
					return true;
				}
			});
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		
		// start state machine
        fsm.ProcessFSM("START");
	}

	@Override
	public void addEventListener(CBEventListener listener) {
		this.listener = listener;
	}

	@Override
	public void removeEventListener(CBEventListener listener) {
		this.listener = null;
	}

	@Override
	public void eventReceived(CBEvent event) {
		// received event handling
	}
}
