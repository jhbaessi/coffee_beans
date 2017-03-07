package com.coffee_beans.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class CBSerializer {

	public static byte[] serialize(Object obj) {
		byte[] bytes = null;
		
		try {
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
			objectStream.writeObject(obj);
			objectStream.close();
			
			bytes = byteStream.toByteArray();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bytes;
	}
	
	public static Object deserialize(byte[] bytes) {
		Object obj = null;
		
		ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
		try {
			ObjectInputStream objectStream = new ObjectInputStream(byteStream);
			obj = objectStream.readObject();			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
}
