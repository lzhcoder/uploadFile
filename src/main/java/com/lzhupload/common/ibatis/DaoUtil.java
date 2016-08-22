package com.lzhupload.common.ibatis;

import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Properties;

import com.ibatis.common.resources.Resources;
import com.ibatis.dao.client.DaoManager;
import com.ibatis.dao.client.DaoManagerBuilder;

 

public class DaoUtil {

	 private static final String resource = "dao.xml";
	  private static final DaoManager daoManager;

	  static {
	    try {
	      daoManager = newDaoManager(null);
	    } catch (Exception e) {
	      throw new RuntimeException("Description.  Cause: " + e, e);
	    }

	  }

	  public static DaoManager getDaoManager() {
	    return daoManager;
	  }

	  public static DaoManager newDaoManager(Properties props) {
	    try {
	      Resources.setCharset(Charset.forName("UTF-8"));
	      Reader reader = Resources.getResourceAsReader(resource);
	      return DaoManagerBuilder.buildDaoManager(reader, props);
	    } catch (Exception e) {
	      throw new RuntimeException("Could not initialize DaoConfig.  Cause: " + e, e);
	    }
	  }

}
