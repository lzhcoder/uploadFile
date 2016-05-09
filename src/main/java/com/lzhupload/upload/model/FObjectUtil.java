package com.lzhupload.upload.model;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.lzhupload.common.impl.AESEncryptor;

public class FObjectUtil {
	private static Logger logger = Logger.getLogger(FObjectUtil.class);
	// 须要设置　origin来源,dbdate时间,sequence数据库序列 例如：A0-2013-4-100012-jpg
	private static String vidFormat = "%s-%s-%s-%s-%s";
	private static String pathFormat = "%s/%s/%s/%s.%s";

	public static void createVid(FObject fobject) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fobject.getDbDate());
		String vids = String.format(vidFormat, fobject.getOrigin(), cal
				.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, fobject
				.getId(), fobject.getExtName());
		fobject.setVidDencrypted(vids);
		fobject.setVid(AESEncryptor.encryptKey(vids));
	}

	public static void encryptVid(FObject fobject) throws Exception {
		String vided = AESEncryptor.decryptKey(fobject.getVid());
		fobject.setVidDencrypted(vided);
		String[] vids = vided.split("-");
		fobject.setOrigin(vids[0]);
		fobject.setId(new Long(vids[3]));
		fobject.setExtName(vids[4]);
		String p = String.format(pathFormat, vids[0], vids[1], vids[2],
				vids[3], vids[4]);
		fobject.setRelativePath(p);
	}

	public static void main(String[] args) {
		Date d = new Date();

		FObject f = new FObject();

		f.setDbDate(d);
		f.setOrigin("A0");
		f.setId(100012l);
		f.setExtName("jpg");
		FObjectUtil fu = new FObjectUtil();
		try {
			fu.createVid(f);
			System.out.println(f.getVid());
			System.out.println(f.getVidDencrypted());

			fu.encryptVid(f);
			System.out.println(f.getExtName());
			System.out.println(f.getOrigin());
			System.out.println(f.getId());
			System.out.println(f.getRelativePath());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
