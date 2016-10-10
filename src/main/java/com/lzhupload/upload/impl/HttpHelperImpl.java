package com.lzhupload.upload.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lzhupload.common.adapter.Message;
import com.lzhupload.common.impl.HttpUtils;
import com.lzhupload.upload.HttpHelper;
import com.lzhupload.upload.model.FObject;
import com.lzhupload.upload.model.MessageModel;

public class HttpHelperImpl extends HttpUtils implements HttpHelper {

	public void doError(HttpServletResponse response, String dataType,
			Exception e) {
		int i = 0;
		try {
			i = new Integer(dataType);
		} catch (Exception ex) {
		}
		response.setStatus(500 + i);
		String errorKey = "ERROR_FILEMANAGER_" + dataType;
		String errorValue = UploadInitImpl.getError().getProperty(errorKey);
		if (e != null) {
			errorValue = String.format(errorValue, e.getMessage());
			e.printStackTrace();
		}
		Message msg = new Message();
		msg.setDataType(errorKey);
		msg.setData(errorValue);
		this.doResponseWithJson(response, msg);
	}

	public boolean doRegisterSuccess(HttpServletRequest request,
			HttpServletResponse response, FObject fobject) {
		try {
			Long[] ids = (Long[]) request.getAttribute("#ids#");
			StringBuilder sb = new StringBuilder();
			for (int i = 0;; i++) {
				if (i == ids.length - 1) {
					sb.append(ids[i]);
					break;
				}
				sb.append(ids[i] + ",");
			}
			MessageModel msg = new MessageModel();
			msg.setDataType("SUCCESS_FILEMANAGER_2");
			Map<String,String> resultMap = new HashMap<String, String>();
			resultMap.put("id", sb.toString());
			msg.setData(resultMap);
//			msg.setData(String.format("{'id':[%s]}", sb.toString()));
			this.doResponseWithJson(response, msg);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean doUploadSuccess(HttpServletRequest request,
			HttpServletResponse response, FObject fobject) {
		try {
			MessageModel msg = new MessageModel();
			msg.setDataType("SUCCESS_FILEMANAGER_1");

			Map<String,Object> resultMap = new HashMap<String, Object>();
			resultMap.put("id", fobject.getId());
			resultMap.put("vid", fobject.getVid());
			msg.setData(resultMap);

//			msg.setData(String.format("{'id':%s,'vid':%s}", fobject
//					.getId(), fobject.getVid()));
			this.doResponseWithJson(response, msg);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

 
	
	public void doSuccess(HttpServletResponse response, String dataType,
			Object object, Exception e) {
		response.setStatus(200);
		String successKey = "SUCCESS_FILEMANAGER_" + dataType;
		String successValue = UploadInitImpl.successReplace(successKey, object);
		if (e != null) {
			successValue = successValue.replace("%error%", e.getMessage());
			e.printStackTrace();
		}
		Message msg = new Message();
		msg.setDataType(successKey);
		msg.setData(successValue);
		this.doResponseWithJson(response, msg);
	}

	public boolean doDownloadSuccess(HttpServletRequest request,
			HttpServletResponse response, FObject fobject) {
		String uploadPath = UploadInitImpl.getUpload()
				.getProperty("uploadPath");
		String fileName = fobject.getFileName();
 
		String fullPath = uploadPath + fobject.getFullPath();
		try {
			// 添加头信息，为"文件下载/另存为"对话框指定默认文件名
			response.addHeader("content-disposition", "attachment;filename="
					+ URLEncoder.encode(fileName, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(fullPath);
			int len = 0;
			byte[] buffer = new byte[1024];
			out = response.getOutputStream();
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

			}
		}
		return true;
	}

	public boolean doVisitSuccess(HttpServletRequest request,
			HttpServletResponse response, FObject fobject) {
		String uploadPath = UploadInitImpl.getUpload()
				.getProperty("uploadPath");
		String fullPath = uploadPath + fobject.getFullPath();
		
		System.out.println("fullPath= "+fullPath);
		
		response.setHeader("Cache-Control", "max-age=10000000");
		InputStream in = null;
		OutputStream out = null;
		try {
			File file = new File(fullPath);
			if (!file.isFile()) {
				doError(response, "25", null);
				return false;
			}
			in = new FileInputStream(file);
			int len = 0;
			byte[] buffer = new byte[1024];
			out = response.getOutputStream();
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}

		} catch (Exception e) {
			doError(response, "26", e);
			return false;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

			}
		}
		return true;
	}

	
 
	public static String getFullUrl(HttpServletRequest request) {

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

	public boolean doGetIdSuccess(HttpServletRequest request,
			HttpServletResponse response, FObject fobject) {
		Long[] ids = (Long[]) request.getAttribute("#ids#");
		StringBuilder sb = new StringBuilder();
		for (int i = 0;; i++) {
			if (i == ids.length - 1) {
				sb.append(ids[i]);
				break;
			}
			sb.append(ids[i] + ",");
		}
		String jsonp=request.getParameter("jsonpcallback");
		String msg=jsonp+"([{id:"+sb.toString()+"}])";
		this.doResponse(response, msg);
		return true;
	}
}
