package com.coffee_beans.network;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smackx.iqregister.AccountManager;

public class CBAccountManager {	
	private static AbstractXMPPConnection connection = null;
    private static AccountManager accountManager = null;
	
	public CBAccountManager() {
		
	}
	
	public static void changePassword(String password){		
		connection = ConnectionManager.getConnection();
		accountManager = AccountManager.getInstance(connection);
		try {
			accountManager.changePassword(password);
		} catch (NoResponseException | XMPPErrorException | NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createAccount(String email, String password){
		connection = ConnectionManager.getConnection();
		accountManager = AccountManager.getInstance(connection);
		
		// username check function will add after RosterListener made.
		
		try {
			if (accountManager.supportsAccountCreation()) {
			    accountManager.sensitiveOperationOverInsecureConnection(true);
			    accountManager.createAccount(email, password);
			}
		} catch (NoResponseException | XMPPErrorException | NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
