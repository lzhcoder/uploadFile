package com.lzhupload.upload.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Properties;

import com.lzhupload.common.adapter.Message;
import com.lzhupload.common.impl.ToolBag;

public class SampleClient {
	public static void main(String[] args) {
		try {
			for (int i = 0; i < 1; i++) {
				new SampleClient().sendEms();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendEms() throws Exception {

		Message msg = new Message();
		msg.setId(2);
		msg.setFromModule("fm");
		msg.setFromModuleSub("fms");
		msg.setToModule("bussness");
		msg.setToModuleSub("test");
		msg.setSendDate("2012-02-18");
		msg.setIsSync("yes");
		msg.setStatus(0);
		msg.setTimeout(1000);
		msg.setDataType("json");
		msg.setData("origin=A0&extName=jpg&count=1");

		String json = ToolBag.getJsonUtil().marshallerUTF8(msg);
		String xml = ToolBag.getXmlUtil().marshallerUTF8(msg);
		String url = "http://localhost:8086/InterfaceAdapterWeb/AdapterServlet";
		url = "http://localhost:8086/FileManager/getvids";

		Properties properties = new Properties();
		properties.setProperty("format", "json");
		properties.setProperty("message", json);

		String tem = postForString(url, properties);
		System.out.println(tem);

	}

	public String postForString(String url, Properties properties)
			throws IOException {
		URL u = new URL(url);
		HttpURLConnection conn = openConnection(u);

		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded; charset=UTF-8");

		prepareConnection(conn, "POST");
		String output = prepareForm(properties);

		output(conn.getOutputStream(), output);
		String inputs = null;
		inputs = input(conn.getInputStream());
		conn.disconnect();
		return inputs;
	}

	protected void output(OutputStream outputStream, String output)
			throws IOException {
		byte[] bypes = output.getBytes();
		outputStream.write(bypes);
	}

	protected String input(InputStream inputStream) throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream));
		String lines;
		while ((lines = reader.readLine()) != null) {
			sb.append(lines).append("\n");
		}
		reader.close();
		return sb.toString();
	}

	protected String prepareForm(Properties properties)
			throws UnsupportedEncodingException {
		StringBuffer params = new StringBuffer();
		Enumeration<?> e = properties.propertyNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = URLEncoder.encode(properties.getProperty(key),
					"UTF-8");
			params.append(key).append("=").append(value).append("&");
		}
		return params.toString();
	}

	private int connectTimeout = -1;
	private int readTimeout = -1;

	protected void prepareConnection(HttpURLConnection connection,
			String httpMethod) throws IOException {
		if (this.connectTimeout >= 0) {
			connection.setConnectTimeout(this.connectTimeout);
		}
		if (this.readTimeout >= 0) {
			connection.setReadTimeout(this.readTimeout);
		}
		connection.setDoInput(true);
		if ("GET".equals(httpMethod)) {
			connection.setInstanceFollowRedirects(true);
		} else {
			connection.setInstanceFollowRedirects(false);
		}
		if ("PUT".equals(httpMethod) || "POST".equals(httpMethod)) {
			connection.setDoOutput(true);
		} else {
			connection.setDoOutput(false);
		}
		connection.setRequestMethod(httpMethod);
	}

	protected HttpURLConnection openConnection(URL url, Proxy proxy)
			throws IOException {
		URLConnection urlConnection = (proxy != null ? url
				.openConnection(proxy) : url.openConnection());
		return (HttpURLConnection) urlConnection;
	}

	protected HttpURLConnection openConnection(URL url) throws IOException {
		URLConnection urlConnection = url.openConnection();
		return (HttpURLConnection) urlConnection;
	}
}
