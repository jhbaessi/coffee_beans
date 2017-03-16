package test.com.coffee_beans.network;

import com.coffee_beans.network.ConnectionManager;

public class ConnectionManagerTest {
	private static final int ENDCOUNT = 10;
	
	ConnectionManager connectionManager = new ConnectionManager();
	
	public static void main(String[] args) {
		int count = 0;
		
		getConnectedStatus();
		ConnectionManager.connectTest();
		getConnectedStatus();
		
		while(true){
			try {
				getConnectedStatus();
				Thread.sleep(1000);
				count++;
				if(count == ENDCOUNT){
					ConnectionManager.disconnect();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private static void getConnectedStatus(){
		System.out.println(ConnectionManager.getConnectedStatus());
	}
}
