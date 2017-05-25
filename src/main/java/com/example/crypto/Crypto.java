package com.example.crypto;

import java.io.Serializable;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto implements Serializable{
	private Cipher encrypter;
	private Cipher decrypter;

	public Crypto(byte[] secretKey, byte[] ivs) throws Exception{
		IvParameterSpec iv = new IvParameterSpec(ivs);

		SecretKeySpec key = new SecretKeySpec(secretKey, "AES");
		encrypter = Cipher.getInstance("AES/CBC/PKCS5Padding");
		encrypter.init(Cipher.ENCRYPT_MODE, key, iv);

		decrypter = Cipher.getInstance("AES/CBC/PKCS5Padding");
		decrypter.init(Cipher.DECRYPT_MODE, key, iv);
	}

	//暗号化処理
	public String encrypto(String text) throws Exception{
		byte[] crypto = encrypter.doFinal(text.getBytes());
		byte[] str64 = Base64.getEncoder().encode(crypto);
		return new String(str64);
	}

	//復号化処理
	public String decrypto(String str64) throws Exception{
		byte[] str = Base64.getDecoder().decode(str64);
		byte[] text = decrypter.doFinal(str);
		return new String(text);
	}
}
