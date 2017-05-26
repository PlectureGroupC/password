package com.example;

import java.util.Comparator;

public class ImageComparator implements Comparator<String>{
	public int compare(String path1, String path2){
		int p1 = Integer.parseInt(path1.replaceAll("[^0-9]",""));
		int p2 = Integer.parseInt(path2.replaceAll("[^0-9]",""));
		if(p1 > p2){
			return 1;
		}else if(p1 == p2){
			return 0;
		}else{
			return -1;
		}
	}
}
