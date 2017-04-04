package com.coffee_beans.network;

import java.io.IOException;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

/** 
 * @JavaDoc This class manage connection to openfire  
 *
 * @author jhbae & tskim
 */ 
public class ConnectionManager {
	private static final String USERNAME = "test";//"admin";
	private static final String PASSWORD = "test";//"smile";
	private static final String SERVICENAME = "localhost";//"127.0.0.1";
	private static final int PORT = 5222;
	private static State isConnected = State.DISCONNECTED;
	
	private static AbstractXMPPConnection conn;

	private enum State{
		CONNECTED,				// Client and server connect
		DISCONNECTED,			// Disconnect the server by the client
		TIMEOUT,				// Login fail
		DISCONNECTEDONERROR;	// Abnormal disconnect
	}
	
	public ConnectionManager() {

	}
	
	public static void connect() {
		XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
				.setUsernameAndPassword(USERNAME, PASSWORD)
				.setServiceName(SERVICENAME)
				.setPort(PORT)
				.setSecurityMode(SecurityMode.disabled)
				.build();
		
		conn = new XMPPTCPConnection(config);
		
		try {
			conn.connect();
			isConnected = State.CONNECTED;	
		} catch (SmackException | IOException | XMPPException e) {
			// TODO Auto-generated catch block
			isConnected = State.TIMEOUT;
			e.printStackTrace();
		}
		
	}

	public void connectTest() {
		XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
				.setServiceName(SERVICENAME)
				.setPort(PORT)
				.setSecurityMode(SecurityMode.disabled)
				.setDebuggerEnabled(true)
				.build();
		
		conn = new XMPPTCPConnection(config);
		
		try {
			conn.connect();
			isConnected = State.CONNECTED;	
		} catch (SmackException | IOException | XMPPException e) {
			// TODO Auto-generated catch block
			isConnected = State.TIMEOUT;
			e.printStackTrace();
		}
	}
		
	public static void disconnect(){
		conn.disconnect();
		isConnected = State.DISCONNECTED;
	}
	
	public static void login(){
		try {
			conn.login();
		} catch (XMPPException | SmackException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static State getConnectedStatus(){
		if(isConnected == State.CONNECTED){
			if(conn.isConnected() == true){
				isConnected = State.CONNECTED;
			} else {
				isConnected = State.DISCONNECTEDONERROR;
			}
		}
		return isConnected;
	}
	
	public static AbstractXMPPConnection getConnection(){
		return conn;
	}
}
