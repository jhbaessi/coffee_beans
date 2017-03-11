package com.coffee_beans.network;

import java.io.IOException;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;


/** 
 * JavaDoc smakc lib를 사용하여 openfire와 연결을 관리하는 클래스입니다.  
 *
 * @author jhbae & tskim
 */ 
public class ConnectionManager {
	public static final String USERNAME = "user_test";//"admin";
	public static final String PASSWORD = "test";//"smile";
	public static final String RESOURCE = "rnhappysmile";
	public static final String SERVICENAME = "localhost";//"127.0.0.1";
	public static final int PORT = 5222;
	
	private AbstractXMPPConnection conn;
	
	public ConnectionManager() {
		
	}
	
	public boolean connect() {
		boolean isConnected = true;
		XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
				.setUsernameAndPassword(USERNAME, PASSWORD)
				.setServiceName(SERVICENAME)
				.setPort(PORT)
				.setSecurityMode(SecurityMode.disabled)
				.setDebuggerEnabled(true)
				.build();
		
		conn = new XMPPTCPConnection(config);
		
		try {
			conn.connect();
		} catch (SmackException | IOException | XMPPException e) {
			// TODO Auto-generated catch block
			isConnected = false;
			e.printStackTrace();
		}
		
		return isConnected;
	}
	
	public void disconnect(){
		conn.disconnect();
	}
	
	public void login(){
		try {
			conn.login();
		} catch (XMPPException | SmackException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void isConnected(){
		System.out.println("connection status: " + conn.isConnected());
	}
}
