package com.snj.apihandler;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.isJson;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.withJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;

import com.snj.exception.AutomationException;
import com.snj.utils.AutomationConstants;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class APIValidations {

	/***
	 * Validate the status code from the response
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param expectedStatusCode
	 * @param responseData
	 * @return
	 */
	public boolean validateStatusCode(Response responseData, int expectedStatusCode) throws AutomationException {
		try {
			int actualStatusCode = responseData.getStatusCode();
			Assert.assertEquals(actualStatusCode, expectedStatusCode,
					actualStatusCode + " is not matches " + expectedStatusCode);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Validate response entire body
	 * 
	 * @author sanojs
	 * @since 02/04/2022
	 * @param response
	 * @param keyToValidate
	 * @param expectedValue
	 */
	public void validateResponseBody(Response response, String expectedValue) {
		try {
			String actualValue = response.then().extract().body().asPrettyString();
			assertTrue(actualValue.equals(expectedValue), actualValue + " value not matches " + expectedValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Validate data in the response body
	 * 
	 * @author sanojs
	 * @since 02/04/2022
	 * @param response
	 * @param keyToValidate
	 * @param expectedValue
	 */
	public void validateInResponseBody(Response response, String expectedValue) {
		try {
			String actualValue = response.then().extract().body().asPrettyString();
			assertTrue(actualValue.contains(expectedValue), actualValue + " value not matches " + expectedValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Validate response data from JSON response
	 * 
	 * @author sanojs
	 * @since 02/04/2022
	 * @param response
	 * @param keyToValidate
	 * @param expectedValue
	 */
	public void validateResponseData(Response response, String keyToValidate, String expectedValue) {
		try {
			String actualValue = response.then().extract().path(keyToValidate);
			assertTrue(actualValue.equals(expectedValue), actualValue + " value not matches " + expectedValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Validate the response array list, the expected array list values can be read
	 * from the JSON file that available at
	 * /src/test/resources/APITesting/ResponseData/ location using
	 * getArrayValuesFromJSONFile() method
	 * 
	 * @author sanojs
	 * @since 02/04/2022
	 * @param arrayName
	 * @param keyName
	 * @param response
	 * @param expectedValue
	 * @return
	 */
	public void validateArrayListInResponse(Response response, String arrayName, String keyName,
			ArrayList<String> expectedValue) {
		String responseBody = response.then().extract().body().asPrettyString();
		JsonPath path = new JsonPath(responseBody);
		int dataSize = path.getInt(arrayName + ".size()");
		for (int i = 0; i < dataSize; i++) {
			String actualValue = path.get("" + arrayName + "[" + i + "]." + keyName + "");
			assertTrue(actualValue.equals(expectedValue.get(i)));
		}
	}

	/**
	 * Validate the actual JSON response against the expected JSON data from the
	 * file
	 * 
	 * @author sanojs
	 * @since 07-03-2023
	 * @param responseJson
	 * @param expectedJSONFileName
	 */
	public void validateJsonResponseMatch(Response responseJson, String expectedJSONFileName) {
		try {
			String expectedResponseDataPath = System.getProperty("user.dir") + AutomationConstants.API_RESPONSE_DATA
					+ expectedJSONFileName + ".json";
			String actualJson = responseJson.getBody().asString();
			String expectedJson = FileUtils.readFileToString(new File(expectedResponseDataPath), "utf-8");
			JSONAssert.assertEquals(expectedJson, actualJson, JSONCompareMode.STRICT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Validate JsonPath from the response
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param responseData
	 * @param jsonPath
	 * @return
	 */
	public boolean validateJsonPath(Response responseData, String jsonPath) throws AutomationException {
		try {
			String responseBody = responseData.getBody().asString();
			assertThat(responseBody, hasJsonPath(jsonPath));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Validate JsonPath to string value
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param responseData
	 * @param jsonPath
	 * @param valueToValidate
	 * @return
	 */
	public boolean validateJsonPathToStringValue(Response responseData, String jsonPath, String valueToValidate)
			throws AutomationException {
		try {
			String responseBody = responseData.getBody().asString();
			assertThat(responseBody, hasJsonPath(jsonPath, equalTo(valueToValidate)));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Validate JsonPath to integer value
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param responseData
	 * @param jsonPath        like data.id
	 * @param valueToValidate
	 * @return
	 */
	public boolean validateJsonPathToIntegerValue(Response responseData, String jsonPath, int valueToValidate)
			throws AutomationException {
		try {
			String responseBody = responseData.getBody().asString();
			assertThat(responseBody, hasJsonPath(jsonPath, equalTo(valueToValidate)));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Validate JsonPath to boolean value
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param responseData
	 * @param jsonPath        like data.id
	 * @param valueToValidate
	 * @return
	 */
	public boolean validateJsonPathToBooleanValue(Response responseData, String jsonPath, boolean valueToValidate)
			throws AutomationException {
		try {
			String responseBody = responseData.getBody().asString();
			assertThat(responseBody, hasJsonPath(jsonPath, equalTo(valueToValidate)));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Validate JsonPath to null value
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param responseData
	 * @param jsonPath        like data.id
	 * @param valueToValidate
	 * @return
	 */
	public boolean validateJsonPathToNullValue(Response responseData, String jsonPath) throws AutomationException {
		try {
			String responseBody = responseData.getBody().asString();
			assertThat(responseBody, isJson(withJsonPath(jsonPath)));
			assertThat(responseBody, hasJsonPath(jsonPath, nullValue()));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Validate the empty response
	 * 
	 * @author sanojs
	 * @since 07-03-2023
	 * @param responseData
	 * @return
	 */
	public boolean validateEmptyResponse(Response responseData) throws AutomationException {
		try {
			String responseBody = responseData.getBody().asString();
			assertTrue(responseBody.equals("{}"), "Response data is not empty");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Validate the status line from the response
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param expectedStatusLine
	 * @param responseData
	 * @return
	 */
	public boolean validateStatusLine(Response responseData, String expectedStatusLine) throws AutomationException {

		try {
			String actualStatusLine = responseData.getStatusLine();
			Assert.assertEquals(actualStatusLine, expectedStatusLine);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Validate the header from the response
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param responseData
	 * @param headerName
	 * @param expectedHeaderValue
	 * @return
	 */
	public boolean validateHeader(Response responseData, String headerName, String expectedHeaderValue)
			throws AutomationException {
		try {
			String actualHeaderValue = responseData.getHeader(headerName);
			Assert.assertEquals(actualHeaderValue, expectedHeaderValue);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Validate the ContentType from the response
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param expectedStatusLine
	 * @param responseData
	 * @return
	 */
	public boolean validateContentType(Response responseData, String expectedContentType) throws AutomationException {

		try {
			String actualContentType = responseData.getContentType();
			Assert.assertEquals(actualContentType, expectedContentType);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Validate the SessionId from the response
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param responseData
	 * @param expectedSessionId
	 * @return
	 */
	public boolean validateSessionId(Response responseData, String expectedSessionId) throws AutomationException {

		try {
			String actualSessionId = responseData.getSessionId();
			Assert.assertEquals(actualSessionId, expectedSessionId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
