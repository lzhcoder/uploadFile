package com.lzhupload.common.impl;

import org.apache.log4j.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertiesUtil {
	private static Logger logger = Logger.getLogger(PropertiesUtil.class);

	public PropertiesUtil() {

	}

	private static Properties errorProperties = null;
	private static Properties configProperties = null;
	private static String errorFileName = "error" + File.separator
			+ "zh_CN.properties";
	private static String configFileName = "config.properties";

	public static synchronized Properties getProperty(String fileName) {
		File file = new File(System.getProperty("user.dir") + File.separator
				+ "conf" + File.separator + fileName);
		try {
			FileInputStream fin = new FileInputStream(file);
			Properties p = new Properties();
			p.load(fin);
			return p;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 中文必须要要用（\u6211\u662F\u7F16\u7801）编码
	public static synchronized String getConfigProperty(String key) {
		if (configProperties == null) {
			configProperties = getProperty(configFileName);
		}
		String value = configProperties.getProperty(key);
		if (value != null) {
			return value;
		}
		return null;
	}

	public static synchronized String getErrorProperty(String key) {
		if (errorProperties == null) {
			errorProperties = getProperty(errorFileName);
		}
		String value = errorProperties.getProperty(key);
		if (value != null) {
			return value;
		}
		return null;
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

	private static String dateFormat = "yyyy-MM-dd HH:mm:ss";

	public static Properties toProperties(Object bean) {
		if (bean == null)
			return null;
		Properties pro = new Properties();
		Class klass = bean.getClass();
		Method[] methods = klass.getMethods();
		Method method;
		String name, key;
		Object obj = null;
		for (int i = 0; i < methods.length; i += 1) {
			try {
				method = methods[i];
				name = method.getName();
				key = "";
				if (name.startsWith("get")) {
					key = name.substring(3);
				} else if (name.startsWith("is")) {
					key = name.substring(2);
				}
				if (key.length() > 0 && !key.equals("Class")
						&& Character.isUpperCase(key.charAt(0))
						&& method.getParameterTypes().length == 0) {
					key = key.substring(0, 1).toLowerCase() + key.substring(1);
					obj = method.invoke(bean, (Object[]) null);
					// 仅处理 String, Number, Boolean, Date 这几种类型
					if (obj == null) {

					} else if (Date.class.isAssignableFrom(obj.getClass())) {
						pro.setProperty(key, parseDate((Date) obj, dateFormat));
					} else if (obj instanceof Object) {
						pro.setProperty(key, obj.toString());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("将java bean转换成为Properties出错", e);
			}
		}
		return pro;
	}

	/** 日期到字符串 */
	public static String parseDate(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(date);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static String parseDate(Date date) {
		if (date == null) {
			return null;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			return sdf.format(date);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println(PropertiesUtil.getProperty("SMS.SEND_MSG"));
	}

}
