package com.lzhupload.upload.model;

import java.util.Calendar;
import java.util.Date;

public class FObject {
	private Long id = 0l;
	private String vid;
	private Date registerDate;
	private Date uploadDate;
	private String relativePath;
	private String extName;
	private String origin;
	private Long del = 0l;
	private Long fileSize = 0l;
	private String note;

	private Date dbDate;
	private String vidDencrypted;

	private int count = 1;

	private String fileName;

	private String fullPath;

	private String userName;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public String getExtName() {
		return extName;
	}

	public void setExtName(String extName) {
		this.extName = extName;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public Long getDel() {
		return del;
	}

	public void setDel(Long del) {
		this.del = del;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getDbDate() {
		return dbDate;
	}

	public void setDbDate(Date dbDate) {
		this.dbDate = dbDate;
	}

	public String getVidDencrypted() {
		return vidDencrypted;
	}

	public void setVidDencrypted(String vidDencrypted) {
		this.vidDencrypted = vidDencrypted;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
