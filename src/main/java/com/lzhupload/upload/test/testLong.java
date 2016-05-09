package com.lzhupload.upload.test;

public class testLong {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long id = 0;
		try {
			id = Long.parseLong(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(id);
	}

}
