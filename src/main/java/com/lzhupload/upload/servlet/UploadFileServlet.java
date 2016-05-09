package com.lzhupload.upload.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.lzhupload.upload.FObjectHelper;
import com.lzhupload.upload.FileHelper;
import com.lzhupload.upload.HttpHelper;
import com.lzhupload.upload.impl.FObjectHelperImpl;
import com.lzhupload.upload.impl.FileHelperImpl;
import com.lzhupload.upload.impl.HttpHelperImpl;
import com.lzhupload.upload.model.FObject;

public class UploadFileServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		ServletFileUpload upload = new ServletFileUpload(
				new DiskFileItemFactory());
		FObject fobject = new FObject();
		FileHelper fileHelper = new FileHelperImpl();
		// 1.获取表单文件对象进行验证
		if (!fileHelper.uploadVerify(request, response, fobject)) {
			return;
		}
		// 2.获取表单参数及验证
		FObjectHelper fObjectHelper = new FObjectHelperImpl();
		if (!fObjectHelper.uploadVerify(request, response, fobject)) {
			return;
		}
		// 3.上传保存文件对象到相应的目录
		if (!fileHelper.upload(request, response, fobject)) {
			return;
		}
		// 4.更新数据库对应的记录
		if (!fObjectHelper.update(request, response, fobject)) {
			return;
		}
		// 5.上传成功返回参数
		HttpHelper httpHelper = new HttpHelperImpl();
		httpHelper.doUploadSuccess(request, response, fobject);
	}
}
