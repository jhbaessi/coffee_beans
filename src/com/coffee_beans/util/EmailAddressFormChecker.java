package com.coffee_beans.util;

import com.sun.xml.internal.fastinfoset.util.StringArray;

public class EmailAddressFormChecker {
	private static final int MAX_NUM_OF_PART = 2;	// the format of email address has two parts:local-part and domain
	
	private static final int INDEX_LOCAL_PART	= 0;
	private static final int INDEX_DOMAIN		= 1;
	
	private static final int MAX_LENGTH_LOCAL_PART = 64;
	private static final int MAX_LENGTH_DOMAIN = 255;
	
	private static final String STR_AT	= "@";
	private static final String STR_DOT	= "\\.";
	
	private static final String CONSECUTIVELY_DOT = "..";
	
	private String address;
	private String localPart;
	private String domain;
	
	public enum Error {
		NO_ERROR							(0),

		ERR_LOCAL_EMPTY						(1),
		ERR_LOCAL_FORBIDDEN_CHARACTER		(2),
		ERR_LOCAL_DOT_LOCATED_FIRST_OR_LAST	(3),
		
		ERR_DOMAIN_EMPTY					(100),
		ERR_DOMAIN_NO_SEPARATE				(101),
		ERR_DOMAIN_CONSECUTIVELY_DOT		(102);
		
		private int errCode;
		
		private Error(int err) {
			errCode = err;
		}
		
		public int getErrorCode() {
			return errCode;
		}
	}
	
	public EmailAddressFormChecker() {
		this(null);
	}
	
	public EmailAddressFormChecker(String address) {
		setAddress(address);
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public boolean isValid() {
		boolean result = true;
		
		if (!address.isEmpty()) {
			String[] splitted = address.split(STR_AT);
			
			if (splitted.length == MAX_NUM_OF_PART) {
				domain = splitted[INDEX_DOMAIN];
				
				// check the domain
				result = (checkDomain() == Error.NO_ERROR) ? true : false;
				
			} else {
				result = false;
			}
		}
		
		return result; 
	}
	
	/**
	 * Not used
	 */
	private Error checkLocalPart() {
		Error err = Error.NO_ERROR;
		boolean valid;
		
		if (localPart.isEmpty()) {
			err = Error.ERR_LOCAL_EMPTY;
		} else {
			valid = localPart.matches("^\\w\\p{Punct}");
			
			if (valid) {
				if ( localPart.startsWith(STR_DOT) || localPart.endsWith(STR_DOT) ) {
					err = Error.ERR_LOCAL_DOT_LOCATED_FIRST_OR_LAST;
				} else {
					// check consecutively dot
					if (localPart.contains(CONSECUTIVELY_DOT)) {
						int indexOfDot = localPart.indexOf(CONSECUTIVELY_DOT);
					}
					
					// check some special characters as below
					// "(),:;<>@[\]
				}
			} else {
				err = Error.ERR_LOCAL_FORBIDDEN_CHARACTER;
			}
		}
		
		return err;
	}
	
	/**
	 * Check up only that a domain has dot-separator.
	 */
	private Error checkDomain() {
		Error err = Error.NO_ERROR;
		
		if (domain.isEmpty()) {
			err = Error.ERR_DOMAIN_EMPTY;
		} else {
			String[] splitted = domain.split(STR_DOT);
			if (splitted.length < 2) {
				err = Error.ERR_DOMAIN_NO_SEPARATE;
			} else {
				for (int index=0; index<splitted.length; index++) {
					if (splitted[index].isEmpty()) {
						err = Error.ERR_DOMAIN_CONSECUTIVELY_DOT;
					}
				}
			}
		}
		
		return err;
	}
	
	public String getLocalPart() {
		return localPart;
	}
	
	public String getDomain() {
		return domain;
	}
}
