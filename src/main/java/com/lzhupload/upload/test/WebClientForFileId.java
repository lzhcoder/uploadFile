package com.lzhupload.upload.test;

import java.io.IOException;

import com.lzhupload.common.adapter.Message;
import com.lzhupload.common.impl.HttpClient;
import com.lzhupload.common.impl.ToolBag;

public class WebClientForFileId {
	public static void main(String[] args) {
		try {
			System.out.println(new WebClientForFileId().getId(1)[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String url = "http://192.168.254.3:8086/FileManager/getIds?count=%s";
	private HttpClient httpClient = new HttpClient();

	public Long[] getId(int count) throws IOException {
		if (count < 1 || count > 10) {
			return null;
		}
		String u = String.format(url, count);
		String json = httpClient.getForString(u);
		Message message = ToolBag.getJsonUtil().unmarshaller(json,
				Message.class);
		if (!"SUCCESS_FILEMANAGER_2".equals(message.getDataType())) {
			return null;
		}
		System.out.println(message.getData());
		WebClientForFileId fileId = ToolBag.getJsonUtil().unmarshaller(
				message.getData(), WebClientForFileId.class);
		return fileId.getId();
	}

	private Long[] id;

	public Long[] getId() {
		return id;
	}

	public void setId(Long[] id) {
		this.id = id;
	}
}
