package com.lzhupload.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lzhupload.upload.model.FObject;

public interface FObjectHelper {
	public boolean uploadVerify(HttpServletRequest request,
			HttpServletResponse response, FObject fobject);

	public boolean update(HttpServletRequest request,
			HttpServletResponse response, FObject fobject);

	public boolean registerVerify(HttpServletRequest request,
			HttpServletResponse response, FObject fobject);

	public boolean register(HttpServletRequest request,
			HttpServletResponse response, FObject fobject);
	
	public boolean visitVerify(HttpServletRequest request,
			HttpServletResponse response, FObject fobject);

	
	public boolean replace(HttpServletRequest request,
			HttpServletResponse response, FObject fobject);
}
