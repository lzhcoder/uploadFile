package com.lzhupload.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lzhupload.upload.model.FObject;

public interface HttpHelper {
	public boolean doUploadSuccess(HttpServletRequest request,
			HttpServletResponse response, FObject fobject);

	public boolean doRegisterSuccess(HttpServletRequest request,
			HttpServletResponse response, FObject fobject);
	public boolean doGetIdSuccess(HttpServletRequest request,
			HttpServletResponse response, FObject fobject);
	public void doError(HttpServletResponse response, String dataType,
			Exception e);

	public boolean doVisitSuccess(HttpServletRequest request,
			HttpServletResponse response, FObject fobject);

	public void doSuccess(HttpServletResponse response, String dataType,
			Object object, Exception e);

}
