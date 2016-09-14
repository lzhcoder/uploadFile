package com.lzhupload.upload.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lzhupload.common.ibatis.BaseDao;
import com.lzhupload.common.ibatis.DaoUtil;
import com.lzhupload.upload.FObjectHelper;
import com.lzhupload.upload.impl.FObjectHelperImpl;
import com.lzhupload.upload.model.FObject;

public class ReplaceFileServlet extends HttpServlet {

	
	/**
	 * Constructor of the object.
	 */
	public ReplaceFileServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}
 
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}
 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
              
		FObject fobject = new FObject();
		
		String id=request.getParameter("id");
		fobject.setId(new Long(id));
		FObjectHelper fObjectHelper = new FObjectHelperImpl();
		fObjectHelper.replace(request, response, fobject);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
