package com.lzhupload.upload.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lzhupload.upload.FObjectHelper;
import com.lzhupload.upload.impl.FObjectHelperImpl;
import com.lzhupload.upload.impl.HttpHelperImpl;
import com.lzhupload.upload.model.FObject;

public class DownloadFileServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		FObject fobject = new FObject();
		FObjectHelper fObjectHelper = new FObjectHelperImpl();
		// 1.获取访问参数进行验证及配置
		if (!fObjectHelper.visitVerify(request, response, fobject)) {
			return;
		}
		// 2.返回请求文件
		HttpHelperImpl httpHelper = new HttpHelperImpl();
		httpHelper.doDownloadSuccess(request, response, fobject);
	}

}