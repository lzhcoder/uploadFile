package com.lzhupload.common.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.TypeReference;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

import com.lzhupload.common.Serialize;

// TODO: Auto-generated Javadoc
/**
 * The Class JacksonJsonSerialize.
 */
public class JacksonJsonSerialize implements Serialize {

	/** The object mapper. */
	private static ObjectMapper objectMapper;

	/**
	 * Gets the object mapper.
	 * 
	 * @return the object mapper
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public ObjectMapper getObjectMapper() throws IOException {
		if (this.objectMapper == null) {
			ObjectMapper objectMapper = new ObjectMapper();
			// 　空属性不序列
			objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
			AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
			DeserializationConfig deserializationConfig = objectMapper
					.getDeserializationConfig();
			deserializationConfig.setDateFormat(new java.text.SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss"));
			SerializationConfig serializationConfig = objectMapper
					.getSerializationConfig();
			serializationConfig.setDateFormat(new java.text.SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss"));
			serializationConfig = serializationConfig.without(
					SerializationConfig.Feature.WRAP_ROOT_VALUE).with(
					SerializationConfig.Feature.INDENT_OUTPUT)
					.withAnnotationIntrospector(introspector);
			objectMapper.setSerializationConfig(serializationConfig);
			this.objectMapper = objectMapper;
		}
		return this.objectMapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fjxhx.common.Serialize#marshaller(java.lang.Object,
	 * java.io.OutputStream)
	 */
	public void marshaller(Object object, OutputStream outputStream) {
		try {
			JsonGenerator jsonGenerator = getObjectMapper().getJsonFactory()
					.createJsonGenerator(outputStream, JsonEncoding.UTF8);
			getObjectMapper().writeValue(jsonGenerator, object);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fjxhx.common.Serialize#marshallerUTF8(java.lang.Object)
	 */
	public String marshallerUTF8(Object object) {
		String json = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JsonGenerator jsonGenerator = getObjectMapper().getJsonFactory()
					.createJsonGenerator(baos, JsonEncoding.UTF8);
			getObjectMapper().writeValue(jsonGenerator, object);
			json = baos.toString("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return json;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fjxhx.common.Serialize#unmarshaller(java.lang.String,
	 * java.lang.Class)
	 */
	public <T> T unmarshaller(String content, Class<T> objectType)
			throws IOException {
		try {
			return getObjectMapper().readValue(content, objectType);
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * Unmarshaller.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param content
	 *            the content
	 * @param objectType
	 *            the object type
	 * @return the t
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public <T> T unmarshaller(String content, TypeReference<T> objectType)
			throws IOException {
		try {
			return getObjectMapper().readValue(content, objectType);
		} catch (IOException e) {
			throw e;
		}
	}
}
