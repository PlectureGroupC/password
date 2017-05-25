package com.example.crypto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileUtil {
	public static void writeBytes(byte[] b, String path) {
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream((path)))){
			out.write(b);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static byte[] readBytes(String path) {
		byte[] b = new byte[16];
		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))){
			in.read(b, 0, 16);
		}catch(Exception e){
			e.printStackTrace();
		}
		return b;
	}
}
