package com.example;

import java.security.MessageDigest;

public class Hash {
	public static String getHash(String rawPass) {
		byte[] cipher_byte;
		StringBuilder sb = null;
		try{
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(rawPass.getBytes());
			cipher_byte = md.digest();
			sb = new StringBuilder(2 * cipher_byte.length);
			for(byte b: cipher_byte) {
				sb.append(String.format("%02x", b&0xff) );
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}