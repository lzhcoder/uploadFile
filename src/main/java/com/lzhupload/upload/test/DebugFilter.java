package com.lzhupload.upload.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class DebugFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		print(request, response);
		filterChain.doFilter(request, response);
	}

	private void print(ServletRequest request, ServletResponse response)
			throws IOException {

		System.out
				.println("请求的URL:" + getFullUrl((HttpServletRequest) request));
		StringBuilder sb = new StringBuilder("提交参数：");
		Enumeration enum_term = request.getParameterNames();
		while (enum_term.hasMoreElements()) {
			String paramName = (String) enum_term.nextElement();
			String paramValue = request.getParameter(paramName);
			sb.append(String.format("%s:%s,", paramName, paramValue));
		}
		System.out.println(sb.toString());

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(ServletInputStream) request.getInputStream()));
		String line = null;
		StringBuilder sbs = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sbs.append(line);
		}

		System.out.println("提交的流内容：" + sbs.toString());
	}

	private String getFullUrl(HttpServletRequest request) {

		StringBuffer url = new StringBuffer();
		String scheme = request.getScheme();
		int port = request.getServerPort();
		if (port < 0)
			port = 80; // Work around java.net.URL bug

		url.append(scheme);
		url.append("://");
		url.append(request.getServerName());
		if ((scheme.equals("http") && (port != 80))
				|| (scheme.equals("https") && (port != 443))) {
			url.append(':');
			url.append(port);
		}
		url.append(request.getRequestURI());

		String queryString = request.getQueryString();

		if (queryString != null)
			url.append('?').append(queryString);

		return url.toString();
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}

}
