package com.lzhupload.upload.impl;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lzhupload.common.ibatis.BaseDao;
import com.lzhupload.common.ibatis.DaoUtil;
import com.lzhupload.common.impl.AESEncryptor;
import com.lzhupload.upload.FObjectHelper;
import com.lzhupload.upload.HttpHelper;
import com.lzhupload.upload.model.FObject;
import com.lzhupload.upload.model.FObjectUtil;

public class FObjectHelperImpl implements FObjectHelper {

	private static Logger logger = Logger.getLogger(FObjectUtil.class);
	// 须要设置　origin来源,dbdate时间,sequence数据库序列 例如：A0-2013-4-100012-jpg
	private static String vidFormat = "%s/%s/%s/%s.%s";
	private static String pathFormat = "%s/%s/%s/";
	private static BaseDao dao = (BaseDao) DaoUtil.getDaoManager().getDao(
			BaseDao.class);
	HttpHelper httpHelper = null;
	private static int idCount = 10;
	static {
		try {
			idCount = Integer.valueOf(UploadInitImpl.getUpload().getProperty(
					"idCount"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FObjectHelperImpl() {
		httpHelper = new HttpHelperImpl();
	}

 
	public boolean register(HttpServletRequest request,
			HttpServletResponse response, FObject fobject) {
		try {
			Long[] ids = new Long[fobject.getCount()];
			for (int i = 0; i < ids.length; i++) {
				ids[i] = dao.insertSqlLong("fobject.addForReg", fobject);
			}
			request.setAttribute("#ids#", ids);
		} catch (Exception e) {
			httpHelper.doError(response, "6", e);
			return false;
		}
		return true;
	}

	
	public boolean registerVerify(HttpServletRequest request,
			HttpServletResponse response, FObject fobject) {
		int count = 1;
		String c = request.getParameter("count");
		if (c != null) {
			try {
				count = Integer.parseInt(c);
			} catch (Exception e) {
				httpHelper.doError(response, "20", e);
				return false;
			}
		}
		if (count > idCount || count < 1) {
			httpHelper.doError(response, "21", null);
			return false;
		}
		fobject.setCount(count);
		return true;
	}

	
	public boolean update(HttpServletRequest request,
			HttpServletResponse response, FObject fobject) {
		fobject.setUploadDate(new Date());
		fobject.setFullPath(fobject.getRelativePath() + fobject.getFileName());
		try {
			dao.updateSql("fobject.modifyForUpload", fobject);
		} catch (Exception e) {
			httpHelper.doError(response, "18", e);
			return false;
		}
		return true;
	}

	public boolean uploadVerify(HttpServletRequest request,
			HttpServletResponse response, FObject fobject) {
		String i = (String) request.getAttribute("id");
		if (i == null || "".equals(i)) {
			i = request.getParameter("id");
		}
		if (i != null && !"".equals(i)) {
			long id = 0;
			// 检查参数能否被转成long
			try {
				id = Long.parseLong(i);
			} catch (Exception e) {
				httpHelper.doError(response, "14", e);
			}
			// 检查参数在数据库有效
			fobject.setId(id);
			try {
				FObject f = (FObject) dao.selectObject("fobject.queryForId",
						fobject);

				String r = (String) request.getAttribute("replace");
				if (r == null || "".equals(i)) {
					r = request.getParameter("replace");
				}
				if (!"1".equals(r) && f.getUploadDate() != null) {
					httpHelper.doSuccess(response, "6", null, null);
					return false;
				}
				fobject.setRegisterDate(f.getRegisterDate());
			} catch (Exception e) {
				httpHelper.doError(response, "6", e);
				return false;
			}
		} else {
			// 注册ID
			try {
				long iidd = dao.insertSqlLong("fobject.addForReg", fobject);
			} catch (Exception e) {
				httpHelper.doError(response, "6", e);
				return false;
			}
		}
		// 设置来源标识
		String origin = (String) request.getAttribute("origin");
		if (origin == null || "".equals(origin)) {
			origin = request.getParameter("origin");
		}
		if (origin == null || "".equals(origin)) {
			origin = UploadInitImpl.getUpload().getProperty("origin");
		}
		if (origin.length() > 2) {
			httpHelper.doError(response, "19", null);
			return false;
		}
		fobject.setOrigin(origin);
		// 设置其它字段
		try {
			createVid(fobject);
		} catch (Exception e) {
			httpHelper.doError(response, "16", null);
			return false;
		}
		try {
			String note = (String) request.getAttribute("note");
			if (note == null || "".equals(note)) {
				i = request.getParameter("note");
			}
			fobject.setNote(note);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	// 创建VID等其它字段
	public static void createVid(FObject fobject) throws Exception {
		// 用系统时间
		Calendar cal = Calendar.getInstance();
		String vids = String.format(vidFormat, fobject.getOrigin(), cal
				.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, fobject
				.getId(), fobject.getExtName());
		fobject.setVidDencrypted(vids);
		fobject.setVid(AESEncryptor.encryptKey(vids));
		String p = String.format(pathFormat, fobject.getOrigin(), cal
				.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
		fobject.setRelativePath(p);
		fobject.setFileName(fobject.getId() + "." + fobject.getExtName());
	}

	public static void encryptVid(FObject fobject) throws Exception {
		String vided = AESEncryptor.decryptKey(fobject.getVid());
		fobject.setVidDencrypted(vided);
		fobject.setFullPath(vided);
		String filename = vided.replaceAll("\\\\", "/");
		filename = filename.substring(filename.lastIndexOf("/") + 1);
		fobject.setFileName(filename);
	}

	
	public boolean visitVerify(HttpServletRequest request,
			HttpServletResponse response, FObject fobject) {
		String vid = request.getParameter("vid");
		if (vid != null) {
			fobject.setVid(vid);
			try {
				encryptVid(fobject);
			} catch (Exception e) {
				httpHelper.doError(response, "22", e);
				return false;
			}
			return true;
		}
		String id = request.getParameter("id");
		if (id != null) {
			try {
				long i = Long.parseLong(id);
				fobject.setId(i);
				FObject f = (FObject) dao.selectObject("fobject.queryForId",
						fobject);

				if (f.getUploadDate() == null || f.getVid() == null) {
					httpHelper.doError(response, "24", null);
					return false;
				}

				fobject.setRelativePath(f.getRelativePath());
				fobject.setExtName(f.getExtName());
				fobject.setVid(f.getVid());
				fobject.setDel(f.getDel());
				fobject.setFileSize(f.getFileSize());
				fobject.setFullPath(f.getFullPath());
				fobject.setFileName(f.getId() + "." + f.getExtName());
			} catch (Exception e) {
				httpHelper.doError(response, "23", e);
				return false;
			}

		}

		return true;
	}
}
