package com.lzhupload.common.impl;

import java.util.Properties;

public class StringUtils {
	public static Properties toProperties(String context, String split,
			String kv) {
		String[] ss = context.split(split);
		Properties p = new Properties();
		for (String s : ss) {
			String[] temp = s.split(kv);
			if (temp.length > 1) {
				p.setProperty(temp[0], temp[1]);
			}
		}
		return p;
	}
}
