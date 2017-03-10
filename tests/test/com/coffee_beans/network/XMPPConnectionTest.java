import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.coffee_beans.network.CBXMPPConnection;

public class XMPPConnectionTest {

	@Test
	public void XMPPConnectionstest() {		
		CBXMPPConnection connection = new CBXMPPConnection();

		assertEquals(true, connection.connection());
	}
}
