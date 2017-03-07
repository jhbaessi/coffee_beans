package com.coffee_beans.network;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.security.cert.CertificateException;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.sasl.SASLMechanism;
import org.jivesoftware.smack.sasl.javax.SASLDigestMD5Mechanism;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

public class CBXMPPConnection {
	
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String RESOURCE = "rnhappysmile";
	public static final String SERVICENAME = "127.0.0.1";
	public static final int PORT = 5222;
	private static AbstractXMPPConnection connection;
	
	public void connection() throws SmackException, IOException, XMPPException, UnrecoverableKeyException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException, java.security.cert.CertificateException, CertificateException{

		SASLMechanism mechanism = new SASLDigestMD5Mechanism();
		SASLAuthentication.registerSASLMechanism(mechanism);
		SASLAuthentication.blacklistSASLMechanism("SCRAM-SHA-1");  
		SASLAuthentication.unBlacklistSASLMechanism("DIGEST-MD5");
		
		// Create the configuration for this new connection
		XMPPTCPConnectionConfiguration configBuilder = XMPPTCPConnectionConfiguration.builder()
		.setUsernameAndPassword(USERNAME, PASSWORD)
		.setResource(RESOURCE)
		.setServiceName(SERVICENAME)
		.setPort(PORT)
		.setSecurityMode(XMPPTCPConnectionConfiguration.SecurityMode.disabled) // TLS(Transport Layer Security) 해제, TLS를 사요하기 위해서는 SPARK 코드의 LocalPreferences를 사용하면 될 듯
		//.setSecurityMode(XMPPTCPConnectionConfiguration.SecurityMode.required )
		.build();
				
		connection = new XMPPTCPConnection(configBuilder);
		// Connect to the server
		
		connection.connect();
		
		// Log into the server
		//connection.login();
				
		//System.out.println("connection status: " + connection.isConnected());
	}
	
	public void discoonect(){
		CBXMPPConnection.connection.disconnect();
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
