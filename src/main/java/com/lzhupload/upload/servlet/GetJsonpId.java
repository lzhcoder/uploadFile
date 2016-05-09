package com.lzhupload.upload.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lzhupload.upload.FObjectHelper;
import com.lzhupload.upload.HttpHelper;
import com.lzhupload.upload.impl.FObjectHelperImpl;
import com.lzhupload.upload.impl.HttpHelperImpl;
import com.lzhupload.upload.model.FObject;

public class GetJsonpId extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		FObject fobject = new FObject();
		// 1.获取表单参数及验证
		FObjectHelper fObjectHelper = new FObjectHelperImpl();
		if (!fObjectHelper.registerVerify(request, response, fobject)) {
			return;
		}
		// 2.数据库生成记录，返回注册ID
		if (!fObjectHelper.register(request, response, fobject)) {
			return;
		}
		// 3.注册成功返回参数
		HttpHelper httpHelper = new HttpHelperImpl();
		httpHelper.doGetIdSuccess(request, response, fobject);
	}

}
