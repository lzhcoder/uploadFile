package com.lzhupload.common;

import java.io.OutputStream;

public abstract interface Serialize
{
  public abstract void marshaller(Object paramObject, OutputStream paramOutputStream);

  public abstract <T> T unmarshaller(String paramString, Class<T> paramClass)
    throws Exception;

  public abstract String marshallerUTF8(Object paramObject);
}