 
package com.lzhupload.upload.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.lzhupload.upload.FileHelper;
import com.lzhupload.upload.HttpHelper;
import com.lzhupload.upload.model.FObject;

public class FileHelperImpl implements FileHelper {
	private static String uploadPath = null;// 上传文件的目录
	private static String tempPath = null; // 临时文件目录
	private static File tempPathFile = null;
	private static int sizeThreshold = 1024;
	private static int sizeMax = 4194304;
	// 初始化
	static {
		sizeMax = Integer.parseInt(UploadInitImpl.getUpload().getProperty(
				"sizeMax"));
		sizeThreshold = Integer.parseInt(UploadInitImpl.getUpload()
				.getProperty("sizeThreshold"));
		uploadPath = UploadInitImpl.getUpload().getProperty("uploadPath");
		tempPath = UploadInitImpl.getUpload().getProperty("tempPath");
		tempPathFile = new File(tempPath);
		if (!tempPathFile.exists()) {
			tempPathFile.mkdirs();
		}
	}
	HttpHelper httpHelper = null;

	public FileHelperImpl() {
		httpHelper = new HttpHelperImpl();
	}

 
	public boolean upload(HttpServletRequest request,
			HttpServletResponse response, FObject fobject) {
		String path = uploadPath + fobject.getRelativePath();
		File uploadFile = new File(path);
		if (!uploadFile.exists()) {
			uploadFile.mkdirs();
		}
		File savedFile = new File(path, fobject.getFileName());
		try {
			FileItem fileItemSave = (FileItem) request
					.getAttribute("#FileItem#");
			System.out.println("fineName:"+fileItemSave.getName());
			System.out.println("fieldName:"+fileItemSave.getFieldName());
			fileItemSave.write(savedFile);
		} catch (Exception e) {
			httpHelper.doError(response, "17", e);
			return false;
		}
		return true;
	}

 
	public boolean uploadVerify(HttpServletRequest request,
			HttpServletResponse response, FObject fobject) {

		// 验证是否是带文件的表单the enctype must be multipart/form-data
		if (!ServletFileUpload.isMultipartContent(request)) {
			httpHelper.doError(response, "10", null);
			return false;
		}
		// 验证表单中只有一个文件
		FileItem fileItem = getFileItem(request);
		if (fileItem == null) {
			httpHelper.doError(response, "11", null);
			return false;
		}
		// 验证文件的扩展名是否合法
		if (!extNameVerify(fileItem, fobject)) {
			httpHelper.doError(response, "12", null);
			return false;
		}
		// 验证文件的大小
		if (!sizeVerify(fileItem, fobject)) {
			httpHelper.doError(response, "13", new Exception(sizeMax + "字节"));
			return false;
		}
		request.setAttribute("#FileItem#", fileItem);
		return true;
	}

	public FileItem getFileItem(HttpServletRequest request) {
		FileItem fileItem = null;
		try {
			// 验证表单文件个数
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(sizeThreshold); // 设置缓冲区大小，这里是4kb
			factory.setRepository(tempPathFile);// 设置缓冲区目录
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");// 解决文件乱码问题
			// upload.setSizeMax(sizeMax);// 设置最大文件尺寸交给sizeVerify方法验证

			List<FileItem> items = upload.parseRequest(request);
			int fileCount = 0;
			Iterator<FileItem> itr = items.iterator();// 所有的表单项
			// 获取文件个数及文件名
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();// 循环获得每个表单项
				if (!item.isFormField()) {// 如果是文件类型
					fileItem = item;
					fileCount++;
				} else {
					request.setAttribute(item.getFieldName(), item.getString());
				}
			}
			if (fileCount > 1) {
				fileItem = null;
			}
		} catch (FileUploadException e) {
			fileItem = null;
		}
		return fileItem;
	}

	private static List<String> allowfiletype = null;

	public boolean extNameVerify(FileItem fileItem, FObject fobject) {
		String fn = fileItem.getName();
		String ext = fn.substring(fn.lastIndexOf(".") + 1);
		fobject.setExtName(ext.toLowerCase());
		Properties properties = UploadInitImpl.getAllowUploadFileType();

		if (allowfiletype == null) {
			allowfiletype = new ArrayList<String>();
			for (Object key : properties.keySet()) {
				String value = (String) properties.get(key);
				String[] values = value.split(",");
				for (String v : values) {
					allowfiletype.add(v.trim());
				}
			}
		}

		// "Mime Type of gumby.gif is image/gif"
		File uploadfile = new File(fileItem.getName());
		String contentType = new MimetypesFileTypeMap().getContentType(
				uploadfile).toLowerCase();

		return allowfiletype.contains(contentType)
				&& properties.keySet().contains(ext);
	}

	public boolean sizeVerify(FileItem fileItem, FObject fobject) {
		fobject.setFileSize(fileItem.getSize());
		return fileItem.getSize() < sizeMax;
	}
}
