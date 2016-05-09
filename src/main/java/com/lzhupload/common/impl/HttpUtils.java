package com.lzhupload.common.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import com.lzhupload.common.Serialize;


// TODO: Auto-generated Javadoc
/**
 * Http工具类
 * 
 */
public class HttpUtils {
	
	/**
	 * Map parameter.
	 *
	 * @param request the request
	 * @param object the object
	 */
	public static void mapParameter(HttpServletRequest request, Object object) {
		Enumeration enum_term = request.getParameterNames();
		while (enum_term.hasMoreElements()) {
			String paramName = (String) enum_term.nextElement();
			try {
				ConvertUtils.register(new Converter() {
					
					/* (non-Javadoc)
					 * @see com.sun.org.apache.commons.beanutils.Converter#convert(java.lang.Class, java.lang.Object)
					 */
					public Object convert(Class type, Object value) {

						// 当value参数等于空时返回空

						if (value == null) {

							return null;

						}

						// 自定义时间的格式为yyyy-MM-dd

						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");

						// 创建日期类对象

						Date dt = null;

						try {

							// 使用自定义日期的格式转化value参数为yyyy-MM-dd格式

							dt = sdf.parse((String) value);

						} catch (java.text.ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						// 返回dt日期对象

						return dt;

					}

				}, Date.class);

				BeanUtils.setProperty(object, paramName, request
						.getParameter(paramName));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * Do response with json.
	 *
	 * @param response the response
	 * @param o the o
	 */
	public static void doResponseWithJson(HttpServletResponse response, Object o) {
		doResponse(response, o, ToolBag.getInstance().getJsonUtil());
	}

	/**
	 * Do response with xml.
	 *
	 * @param response the response
	 * @param o the o
	 */
	public static void doResponseWithXml(HttpServletResponse response, Object o) {
		doResponse(response, o, ToolBag.getInstance().getXmlUtil());
	}

	/**
	 * Do response.
	 *
	 * @param response the response
	 * @param data the data
	 */
	public static void doResponse(HttpServletResponse response, String data) {
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.println(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}

	}

	/**
	 * Do response.
	 *
	 * @param response the response
	 * @param o the o
	 * @param s the s
	 */
	public static void doResponse(HttpServletResponse response, Object o,
			Serialize s) {
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("text/plain; charset=UTF-8");
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			s.marshaller(o, os);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
