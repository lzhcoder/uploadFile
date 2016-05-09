package com.lzhupload.common.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class HttpClient {
	public static void main(String[] args) {
		Properties properties = new Properties();
		String url = "http://192.168.254.3:8086/FileManager/getIds";
		HttpClient hc = new HttpClient();
	}

	public String getForString(String url) throws IOException {
		URL u = new URL(url);
		HttpURLConnection conn = openConnection(u);
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded; charset=UTF-8");
		prepareConnection(conn, "GET");
		String inputs = null;
		inputs = input(conn.getInputStream());
		conn.disconnect();
		return inputs;
	}

	public String getForString(String url, Properties properties)
			throws IOException {
		if (properties != null) {
			String output = parseForm(properties);
			if (output != null) {
				url = url + "?" + output;
			}
		}
		return getForString(url);
	}

	public String postForString(String url, Properties properties)
			throws IOException {
		URL u = new URL(url);
		HttpURLConnection conn = openConnection(u);

		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded; charset=UTF-8");

		prepareConnection(conn, "POST");
		String output = parseForm(properties);
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

	protected String parseForm(Properties properties)
			throws UnsupportedEncodingException {
		StringBuffer params = new StringBuffer();
		Enumeration<?> e = properties.propertyNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = URLEncoder.encode(properties.getProperty(key),
					"UTF-8");
			params.append(key).append("=").append(value).append("&");
		}
		String s = params.toString();
		if (s.length() > 0) {
			return s.substring(0, s.length() - 1);
		}
		return null;
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

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

}
