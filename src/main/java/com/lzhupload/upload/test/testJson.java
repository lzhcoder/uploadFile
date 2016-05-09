package com.lzhupload.upload.test;

import java.io.IOException;

import com.lzhupload.common.impl.ToolBag;

public class testJson {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			testM t=ToolBag.getJsonUtil().unmarshaller("{\"id\":12,\"name\":\"czp\"}", testM.class);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
