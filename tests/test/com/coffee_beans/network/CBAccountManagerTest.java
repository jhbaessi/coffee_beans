package test.com.coffee_beans.network;

import java.io.IOException;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import com.coffee_beans.network.CBAccountManager;
import com.coffee_beans.network.ConnectionManager;

public class CBAccountManagerTest {
	private static final int SLEEPTIME = 5000;
	
	private static AbstractXMPPConnection connection = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConnectionManager.connectTest();
		getConnectedStatus();
		
		// createAccount unit test
		//CBAccountManager.createAccount("tskim", "test");
		
		
		// changePassword unit test
		login("tskim", "test");
		CBAccountManager.changePassword("test1");
		
		sleep(SLEEPTIME);
		disconnect();
		sleep(SLEEPTIME);
		
		ConnectionManager.connectTest();
		login("tskim", "test1");
		CBAccountManager.changePassword("test");
		
		sleep(SLEEPTIME);
		disconnect();
	}
		
	private static void getConnectedStatus(){
		System.out.println(ConnectionManager.getConnectedStatus());
	}
	
	private static void login(String username, String password) {
			connection = ConnectionManager.getConnection();
		try {
			connection.login(username, password);
		} catch (XMPPException | SmackException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void disconnect() {
		connection = ConnectionManager.getConnection();
		connection.disconnect();
	}
	
	private static void sleep(long time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
