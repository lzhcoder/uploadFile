package com.lzhupload.common.impl;

// TODO: Auto-generated Javadoc
/**
 * 
 * fjxhx 工具包类<br/>
 * JSON格式化对象
 * XML格式化对象
 * 
 * 
 */
public class ToolBag {
	
	/** The json util. */
	private static JacksonJsonSerialize jsonUtil = null;
	
	/** The xml util. */
	private static JaxbXmlSerialize xmlUtil = null;
	static {
		jsonUtil = new JacksonJsonSerialize();
		xmlUtil = new JaxbXmlSerialize();
	}

	/** The unique instance. */
	private static ToolBag uniqueInstance = null;

	/**
	 * Gets the single instance of ToolBag.
	 *
	 * @return single instance of ToolBag
	 */
	public static ToolBag getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new ToolBag();
		}
		return uniqueInstance;
	}

	/**
	 * Gets the json util.
	 *
	 * @return the json util
	 */
	public static JacksonJsonSerialize getJsonUtil() {
		return getInstance().jsonUtil;
	}

	/**
	 * Gets the xml util.
	 *
	 * @return the xml util
	 */
	public static JaxbXmlSerialize getXmlUtil() {
		return getInstance().xmlUtil;
	}

}
