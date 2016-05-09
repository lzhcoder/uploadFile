package com.lzhupload.upload.test;

 
public class testPath {

	/**
	 * @param args
	 */
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String p = "A0/2013/7/101328.jpg";
		String file = p.replaceAll("\\\\", "/");
		file = file.substring(file.lastIndexOf("/") + 1);
		System.out.println(file);
	}

}
