package com.snj.apihandler;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.snj.exception.AutomationException;
import com.snj.utils.AutomationConstants;

import io.restassured.response.Response;

public class APIUtilities {

	/**
	 * Get the value from JSON response
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param responseData
	 * @param parameterName
	 * @return
	 */
	public String getValueFromResponse(Response responseData, String parameterName) throws AutomationException {
		String jsonValue = " ";
		try {
			jsonValue = responseData.then().extract().path(parameterName);
			System.out.println("Value of " + parameterName + " is " + jsonValue);
		} catch (Exception e) {
			System.out.println("Failed to get the value from the response");
		}
		return jsonValue;
	}

	/**
	 * Get array values from JSON file - specify the response JSON file in
	 * /src/test/resources/APITesting/ResponseData/ folder and pass the JSONFileName
	 * 
	 * @author sanojs
	 * @since 07-02-2022
	 * @param JSONFilePath
	 * @param arrayName
	 * @param keyName
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	public ArrayList<String> getArrayValuesFromJSONFile(String JSONFileName, String arrayName, String keyName)
			throws FileNotFoundException, IOException, ParseException {
		String value = null;
		String finalValue;
		ArrayList<String> listValue = new ArrayList<>();
		JSONParser jsonParser = new JSONParser();
		String responseDataPath = System.getProperty("user.dir") + AutomationConstants.API_RESPONSE_DATA + JSONFileName
				+ ".json";
		JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(responseDataPath));
		JSONArray jsonArray = (JSONArray) jsonObject.get(arrayName);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonobject = (JSONObject) jsonArray.get(i);
			value = (String) jsonobject.get(keyName);
			finalValue = new String(value.getBytes("ISO-8859-1"), "UTF-8");
			listValue.add(finalValue);
		}
		return listValue;
	}
}
