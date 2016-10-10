package com.lzhupload.upload.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lzhupload.upload.FObjectHelper;
import com.lzhupload.upload.FileHelper;
import com.lzhupload.upload.HttpHelper;
import com.lzhupload.upload.impl.FObjectHelperImpl;
import com.lzhupload.upload.impl.FileHelperImpl;
import com.lzhupload.upload.impl.HttpHelperImpl;
import com.lzhupload.upload.model.FObject;

public class CopyFileServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public CopyFileServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request,response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		FObject fobject = new FObject();
	 
		FObjectHelper fObjectHelper = new FObjectHelperImpl();
 
		
		String id=request.getParameter("id");
		fobject.setId(new Long(id));
		
		if (!fObjectHelper.copy(request, response, fobject)) {
			return;
		}
 
		HttpHelper httpHelper = new HttpHelperImpl();
		httpHelper.doUploadSuccess(request, response, fobject); 
		
		
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
