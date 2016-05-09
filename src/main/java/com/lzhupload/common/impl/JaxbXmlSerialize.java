package com.lzhupload.common.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.lzhupload.common.Serialize;

// TODO: Auto-generated Javadoc
/**
 * The Class JaxbXmlSerialize.
 */
public class JaxbXmlSerialize implements Serialize {

	/** The jaxb context hash map. */
	private static Map<Class, JAXBContext> jaxbContextHashMap = new ConcurrentHashMap<Class, JAXBContext>();

	/**
	 * Builds the marshaller.
	 * 
	 * @param objectType
	 *            the object type
	 * @return the marshaller
	 * @throws JAXBException
	 *             the jAXB exception
	 */
	private Marshaller buildMarshaller(Class<?> objectType)
			throws JAXBException {
		if (!jaxbContextHashMap.containsKey(objectType)) {
			JAXBContext context = JAXBContext.newInstance(objectType);
			jaxbContextHashMap.put(objectType, context);
		}
		JAXBContext context = jaxbContextHashMap.get(objectType);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
		return marshaller;
	}

	public static Map<Class, JAXBContext> getJaxbContextHashMap() {
		return jaxbContextHashMap;
	}

	public static void setJaxbContextHashMap(
			Map<Class, JAXBContext> jaxbContextHashMap) {
		JaxbXmlSerialize.jaxbContextHashMap = jaxbContextHashMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fjxhx.common.Serialize#marshaller(java.lang.Object,
	 * java.io.OutputStream)
	 */
	public void marshaller(Object object, OutputStream outputStream) {
		try {
			Marshaller m = buildMarshaller(object.getClass());
			m.marshal(object, outputStream);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fjxhx.common.Serialize#marshallerUTF8(java.lang.Object)
	 */
	public String marshallerUTF8(Object object) {
		String xml = null;
		try {
			Marshaller m = buildMarshaller(object.getClass());

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			m.marshal(object, baos);

			xml = baos.toString("UTF-8");

		} catch (JAXBException e) {
			e.printStackTrace();
		} finally {
			return xml;
		}
	}

	/**
	 * Builds the unmarshaller.
	 * 
	 * @param objectType
	 *            the object type
	 * @return the unmarshaller
	 * @throws JAXBException
	 *             the jAXB exception
	 */
	private Unmarshaller buildUnmarshaller(Class<?> objectType)
			throws JAXBException {
		if (!jaxbContextHashMap.containsKey(objectType)) {
			JAXBContext context = JAXBContext.newInstance(objectType);
			jaxbContextHashMap.put(objectType, context);
		}
		JAXBContext context = jaxbContextHashMap.get(objectType);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		// unmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		// unmarshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
		return unmarshaller;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fjxhx.common.Serialize#unmarshaller(java.lang.String,
	 * java.lang.Class)
	 */
	public <T> T unmarshaller(String content, Class<T> objectType)
			throws Exception {
		try {
			Unmarshaller unmarshaller = buildUnmarshaller(objectType);
			StringReader reader = new StringReader(content);

			return (T) unmarshaller.unmarshal(reader);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Unmarshaller.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param inputStream
	 *            the input stream
	 * @param objectType
	 *            the object type
	 * @return the t
	 * @throws Exception
	 *             the exception
	 */
	public <T> T unmarshaller(InputStream inputStream, Class<T> objectType)
			throws Exception {
		try {
			Unmarshaller unmarshaller = buildUnmarshaller(objectType);

			return (T) unmarshaller.unmarshal(inputStream);
		} catch (Exception e) {
			throw e;
		}
	}

}
