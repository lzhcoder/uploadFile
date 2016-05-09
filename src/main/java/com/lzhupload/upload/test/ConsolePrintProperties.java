package com.lzhupload.upload.test;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsolePrintProperties {
	public void print(Object arg) {
		Class klass = arg.getClass();
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
					obj = method.invoke(arg, (Object[]) null);
					// 仅处理 String, Number, Boolean, Date 这几种类型
					if (obj == null) {

					} else if (Date.class.isAssignableFrom(obj.getClass())) {

						SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						System.out.println(key + ":"
								+ DATE_TIME_FORMAT.format((Date) obj));

					} else if (obj.getClass().isPrimitive()
							|| obj.getClass().equals(String.class)
							|| obj.getClass().equals(Boolean.class)
							|| Number.class.isAssignableFrom(obj.getClass())) {
						System.out.println(key + ":" + obj);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
