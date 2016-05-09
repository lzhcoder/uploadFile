package com.lzhupload.upload.test;

import com.lzhupload.common.ibatis.BaseDao;
import com.lzhupload.common.ibatis.DaoUtil;
import com.lzhupload.upload.model.FObject;

public class testFobject {
	public static void main(String[] args) {
		BaseDao dao = (BaseDao) DaoUtil.getDaoManager().getDao(BaseDao.class);
		// FileRegisterImpl fr=new FileRegisterImpl();
		// String vid=fr.getVid("A0", "jpg");
		//		
		FObject f = new FObject();
		// f.setVid(vid);
		// try {
		// FObjectUtil.encryptVid(f);
		// } catch (Exception e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// f.setFileName("filename");
		// f.setRegisterDate(new Date());
		// f.setUploadDate(new Date());
		// f.setNote("test");
		//
		// try {
		// dao.insertSql("fobject.add", f);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// try {
		// f.setId(100034);
		// FObject f1 = (FObject) dao.selectObject("fobject.queryForId", f);
		// ConsolePrintProperties c = new ConsolePrintProperties();
		// c.print(f1);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		try {
			f.setExtName("jpg");
			f.setFileSize(100l);
			long i = dao.insertSqlLong("fobject.addForReg", f);
			System.out.println(i);
			ConsolePrintProperties c = new ConsolePrintProperties();
			c.print(f);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
