package com.coffee_beans.network;

import java.io.IOException;

import javax.net.ssl.SSLSocketFactory;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

public class CBXMPPConnection {
	
	public static final String USERNAME = "admin";
	public static final String PASSWORD = "smile";
	public static final String RESOURCE = "rnhappysmile";
	public static final String SERVICENAME = "127.0.0.1";
	public static final int PORT = 5222;
	public static AbstractXMPPConnection connection;
	
	public boolean connection() {
		boolean isConnected = false;
		
//		SASLMechanism mechanism = new SASLDigestMD5Mechanism();
//		SASLAuthentication.registerSASLMechanism(mechanism);
//		SASLAuthentication.blacklistSASLMechanism("SCRAM-SHA-1");  
//		SASLAuthentication.unBlacklistSASLMechanism("DIGEST-MD5");
		
		// Create the configuration for this new connection
		XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
		configBuilder.setSecurityMode(XMPPTCPConnectionConfiguration.SecurityMode.ifpossible); // TLS(Transport Layer Security) 해제, TLS를 사요하기 위해서는 SPARK 코드의 LocalPreferences를 사용하면 될 듯
		configBuilder.setSocketFactory(SSLSocketFactory.getDefault()); // setSocketFactory를 통해 AssertionError 문제 해결됨 
		configBuilder.setUsernameAndPassword(USERNAME, PASSWORD);
		configBuilder.setResource(RESOURCE);
		configBuilder.setServiceName(SERVICENAME);
		configBuilder.setPort(PORT);
		configBuilder.setCompressionEnabled(false);
		configBuilder.setSendPresence(true);
		configBuilder.setDebuggerEnabled(true);
			
		AbstractXMPPConnection clientConnection = new XMPPTCPConnection(configBuilder.build());
		if(clientConnection == null) {
			return false;
		}
		
		clientConnection.setPacketReplyTimeout(10000);
		isConnected = true;
		
		try {
			clientConnection.connect();
		} catch (SmackException e) {
			// TODO Auto-generated catch block
			isConnected = false;
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			isConnected = false;
			e.printStackTrace();
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			isConnected = false;
			e.printStackTrace();
		}
		
		// Log into the server
		//connection.login();
				
		//System.out.println("connection status: " + connection.isConnected());
		
		return isConnected;
	}
	
	public boolean discoonect(){
		CBXMPPConnection.connection.disconnect();
		return true;
	}
	
	public void XMPPlogin(String name, String password){
		try {
			connection.login(name, password);
		} catch (XMPPException | SmackException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void XMPPStatus(){
		System.out.println("connection status: " + connection.isConnected());
	}
}
