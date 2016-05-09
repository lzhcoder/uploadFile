package com.lzhupload.common.adapter;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="message")
public class Message
  implements Cloneable, Serializable
{
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
  private String data;

  public int getId()
  {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFromModule() {
    return this.fromModule;
  }

  public void setFromModule(String fromModule) {
    this.fromModule = fromModule;
  }

  public String getFromModuleSub() {
    return this.fromModuleSub;
  }

  public void setFromModuleSub(String fromModuleSub) {
    this.fromModuleSub = fromModuleSub;
  }

  public String getToModule() {
    return this.toModule;
  }

  public void setToModule(String toModule) {
    this.toModule = toModule;
  }

  public String getToModuleSub() {
    return this.toModuleSub;
  }

  public void setToModuleSub(String toModuleSub) {
    this.toModuleSub = toModuleSub;
  }

  public String getSendDate() {
    return this.sendDate;
  }

  public void setSendDate(String sendDate) {
    this.sendDate = sendDate;
  }

  public String getIsSync() {
    return this.isSync;
  }

  public void setIsSync(String isSync) {
    this.isSync = isSync;
  }

  public int getStatus() {
    return this.status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public long getTimeout() {
    return this.timeout;
  }

  public void setTimeout(long timeout) {
    this.timeout = timeout;
  }

  public String getDataType() {
    return this.dataType;
  }

  public void setDataType(String dataType) {
    this.dataType = dataType;
  }

  public String getData() {
    return this.data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public Object clone()
  {
    Message o = null;
    try {
      o = (Message)super.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return o;
  }
}