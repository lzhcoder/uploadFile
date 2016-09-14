package com.lzhupload.upload.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class DebugFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		System.out.println("请求的URL:" + getFullUrl((HttpServletRequest) request));
		printParameter(request, response);
		filterChain.doFilter(request, response);
		printAttribute(request, response);
	}

	private void printParameter(ServletRequest request, ServletResponse response)
			throws IOException {
		StringBuilder sb = new StringBuilder("提交参数：");
		Enumeration enum_term = request.getParameterNames();
		while (enum_term.hasMoreElements()) {
			String paramName = (String) enum_term.nextElement();
			String paramValue = request.getParameter(paramName);
			sb.append(String.format("%s:%s,", paramName, paramValue));
		}
		System.out.println(sb.toString());
	}

	private void printAttribute(ServletRequest request, ServletResponse response)
			throws IOException {
		StringBuilder sb1 = new StringBuilder("打包在文件中的参数：");
		Enumeration enum_term1 = request.getAttributeNames();
		while (enum_term1.hasMoreElements()) {
			String paramName = (String) enum_term1.nextElement();
			Object obj = request.getAttribute(paramName);
			if (obj instanceof String) {
				String paramValue = (String)obj;
				sb1.append(String.format("%s:%s,", paramName, paramValue));
			}else{
				sb1.append(String.format("noString:%s,", obj.getClass().getName()));
			}

		}
		System.out.println(sb1.toString());
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
