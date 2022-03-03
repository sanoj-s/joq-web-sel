package com.snj.apihandler;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.isJson;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.withJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

import org.testng.Assert;

import com.snj.exception.AutomationException;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class APIValidationActions {
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
			JsonPath jsonpath = responseData.jsonPath();
			jsonValue = jsonpath.get(parameterName);
			System.out.println("Value of " + parameterName + " is " + jsonValue);
		} catch (Exception e) {
			System.out.println("Failed to get the value from the response");
		}
		return jsonValue;
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
	 * @param jsonPath
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
	 * @param jsonPath
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
	 * @param jsonPath
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
			Assert.assertEquals(actualStatusCode, expectedStatusCode);
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

	/**
	 * Print all header names and values
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param responseData
	 */
	public void printAllHeaders(Response responseData) throws AutomationException {

		try {
			System.out.println("HEADERS VALUES");
			System.out.println("==============");
			Headers allheaders = responseData.getHeaders();
			for (Header header : allheaders) {
				System.out.println(header.getName() + "===>" + header.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
