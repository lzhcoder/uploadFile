package com.lzhupload.upload.model;

 

public class MessageModel {
	
	//执行代码
	private String code;
	//执行结果
	private String reason;
	//正确的时候才有空值，不用解析
	private String message;
	
	private Object data;
	

	private int id;
	private String fromModule;
	private String fromModuleSub;
	private String toModule;
	private String toModuleSub;
	private String sendDate;
	private String isSync;
	private int status;
	private long timeout;
	private String dataType;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFromModule() {
		return fromModule;
	}

	public void setFromModule(String fromModule) {
		this.fromModule = fromModule;
	}

	public String getFromModuleSub() {
		return fromModuleSub;
	}

	public void setFromModuleSub(String fromModuleSub) {
		this.fromModuleSub = fromModuleSub;
	}

	public String getToModule() {
		return toModule;
	}

	public void setToModule(String toModule) {
		this.toModule = toModule;
	}

	public String getToModuleSub() {
		return toModuleSub;
	}

	public void setToModuleSub(String toModuleSub) {
		this.toModuleSub = toModuleSub;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getIsSync() {
		return isSync;
	}

	public void setIsSync(String isSync) {
		this.isSync = isSync;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
