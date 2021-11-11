package com.snj.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.snj.exception.AutomationException;

public class PropertyDataHandler {

	/**
	 * Returns specified property value from the given property file
	 * 
	 * @author sanojs
	 * @since 08-05-2021
	 * @param fileName
	 * @param propertName
	 * @return propValue
	 * @throws Exception
	 */
	public String getProperty(String fileName, String propertyName) throws AutomationException {
		String propValue = "";
		try {
			Properties props = new Properties();
			ClassLoader classLoader = PropertyDataHandler.class.getClassLoader();
			InputStream input = classLoader.getResourceAsStream(fileName + ".properties");
			props.load(input);
			propValue = props.getProperty(propertyName);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return propValue;
	}

}
