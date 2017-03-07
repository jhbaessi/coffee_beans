package test.com.coffee_beans.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.coffee_beans.util.EmailAddressFormChecker;

public class EmailAddressFormCheckerTest {
	private static final String[] testAddresses = {
		"Abc@example.com",
		
		"Abc.example.com",
		"A@b@c@example.com",
		"Abc@example",
		"Abc@example.",
		"Abc@example..",
		"Abc@example..com",
	};
	
	@Test
	public void testFormCheck() {
		EmailAddressFormChecker formChecker = new EmailAddressFormChecker();
		
		int indexOfAddress = 0;
		
		formChecker.setAddress(testAddresses[indexOfAddress]);
		System.out.println("Set address - " + testAddresses[indexOfAddress++]);
		
		assertTrue(formChecker.isValid());
		
		for (; indexOfAddress<testAddresses.length; indexOfAddress++) {
			formChecker.setAddress(testAddresses[indexOfAddress]);
			System.out.println("Set address - " + testAddresses[indexOfAddress]);
			
			assertFalse(formChecker.isValid());
		}
	}
}