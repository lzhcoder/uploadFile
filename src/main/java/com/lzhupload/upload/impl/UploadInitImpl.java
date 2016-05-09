 
package com.lzhupload.upload.impl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.lzhupload.common.impl.PropertiesUtil;
import com.lzhupload.upload.UploadInit;

public class UploadInitImpl implements UploadInit, ServletContextListener {
	private static Logger logger = Logger.getLogger(UploadInitImpl.class);
	private static Properties allowUploadFileType = null, upload = null,
			error = null, success = null;

	public static Properties createProperties(String path) {
		Properties properties = null;
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(path));
			properties = new Properties();
			properties.load(in);
		} catch (IOException e) {
			logger.error(e);
		}
		return properties;
	}

	public void loadProperties() {

	}

	
	public void contextDestroyed(ServletContextEvent sce) {

	}

	
	public void contextInitialized(ServletContextEvent sce) {
		// 取得该应用的ServletContext实例
		ServletContext application = sce.getServletContext();
		String aft = application.getRealPath(application
				.getInitParameter("allowUploadFileType"));

		String u = application.getRealPath(application
				.getInitParameter("upload"));

		String e = application.getRealPath(application
				.getInitParameter("error"));

		String s = application.getRealPath(application
				.getInitParameter("success"));

		allowUploadFileType = createProperties(aft);
		upload = createProperties(u);
		error = createProperties(e);
		success = createProperties(s);

		String web_path = application.getRealPath("").replace("\\", "/");
		String path = "<web_path>";
		Enumeration eu = upload.propertyNames();
		while (eu.hasMoreElements()) {
			String key = (String) eu.nextElement();
			String value = upload.getProperty(key).replaceAll("<web_path>",
					web_path);
			upload.setProperty(key, value);
		}

	}

	public static Properties getAllowUploadFileType() {
		return allowUploadFileType;
	}

	public static void setAllowUploadFileType(Properties allowUploadFileType) {
		UploadInitImpl.allowUploadFileType = allowUploadFileType;
	}

	public static Properties getUpload() {
		return upload;
	}

	public static void setUpload(Properties upload) {
		UploadInitImpl.upload = upload;
	}

	public static Properties getError() {
		return error;
	}

	public static void setError(Properties error) {
		UploadInitImpl.error = error;
	}

	public static Properties getSuccess() {
		return success;
	}

	public static void setSuccess(Properties success) {
		UploadInitImpl.success = success;
	}

	public static String successReplace(String key, Object object) {
		if (UploadInitImpl.success == null) {
			return "config not init!";
		}
		if (key == null) {
			return "key is null!";
		}
		String value = UploadInitImpl.success.getProperty(key);
		if (value == null) {
			return "the key find value is null!";
		}
		if (object == null) {
			return value;
		}
		return objectReplace(value, object);
	}

	public static String objectReplace(String value, Object object) {
		if (value == null) {
			return null;
		}
		String regex = "\\$([^\\$]+)\\$";// 匹配所有 $hello$ 这类标记
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(value);
		while (m.find()) {
			String pre = m.group(1);
			String getFun = "get" + pre.substring(0, 1).toUpperCase()
					+ pre.substring(1);
			Class klass = object.getClass();
			try {
				Method method = klass.getMethod(getFun);
				Object obj = method.invoke(object);
				if (obj == null) {
					obj = "";
				}
				if (Date.class.isAssignableFrom(obj.getClass())) {
					obj = PropertiesUtil.parseDate((Date) obj);
				}
				value = value.replaceAll("\\$" + pre + "\\$", obj.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return value;
	}

}
