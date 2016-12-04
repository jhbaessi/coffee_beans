package test.com.coffee_beans.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.coffee_beans.common.Account;
import com.coffee_beans.util.CBSerializer;

public class CBSerializerTest {
	private static final String email = "test@coffeebeans.com";
	private static final String password = "testpasword1234%^&*";
	
	@Test
	public void testSerializer() {
		Account sourceAccount = new Account(email, password);
		
		byte[] bytes = CBSerializer.serialize(sourceAccount);
		
		Account deserializedAccount = (Account)CBSerializer.deserialize(bytes);
		
		assertEquals(email, deserializedAccount.getEmail());
		assertEquals(password, deserializedAccount.getPassword());
	}
}
