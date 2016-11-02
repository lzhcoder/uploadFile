package com.lzhupload.rest;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.wink.common.annotations.Workspace;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.lzhupload.common.ibatis.BaseDao;
import com.lzhupload.common.ibatis.DaoUtil;
import com.lzhupload.common.json.JsonUtil1;
import com.lzhupload.common.json.JsonUtil2;
import com.lzhupload.common.json.JsonUtil3;
import com.lzhupload.upload.model.FObject;

@Resource
@Path("/file")
@Service
@Workspace(collectionTitle="fileServic",workspaceTitle="File Service Resource")
public class FileServiceResource{
	
	 @Context 
	 private HttpServletRequest request;  
	 
	 private static BaseDao dao = (BaseDao) DaoUtil.getDaoManager().getDao(BaseDao.class);
 
 
	@GET
	@Path("/get/{id}")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject findDeptById( @PathParam("id") Long id){
		 
		FObject fObject=new FObject();
		fObject.setId(id);
		try {
			fObject =	(FObject) dao.selectObject("fobject.queryForId", fObject);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject	result=JsonUtil1.toJSONObject(fObject);
		
		return result;
	}
	
	 
}
