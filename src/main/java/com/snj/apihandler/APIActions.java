package com.snj.apihandler;

import java.io.File;
import java.util.Map;

import com.snj.exception.AutomationException;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class APIActions {

	public static String responseInString;

	/**
	 * Perform HTTP Post request with in-line body data
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param baseURI
	 * @param endPointPath
	 * @param queryParams
	 * @param body
	 * @return
	 * @throws AutomationException
	 */
	public Response postRequest(String baseURI, String contentType, Map<String, String> queryParams,
			Map<String, String> body, String endPointPath) throws AutomationException {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).queryParams(queryParams).body(body).contentType(contentType)
					.log().all().when().post(endPointPath).andReturn();
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
		} catch (Exception e) {
			throw new AutomationException(e.getMessage());
		}
		return response;
	}

	/**
	 * Perform HTTP Post request with body data from file
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param baseURI
	 * @param requestFilePath
	 * @param endPointPath
	 * @return
	 * @throws AutomationException
	 */

	public Response postRequest(String baseURI, String requestFilePath, String contentType, String endPointPath)
			throws AutomationException {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).body(new File(requestFilePath).getAbsoluteFile())
					.contentType(contentType).log().all().when().post(endPointPath).andReturn();
			response.then().log().all().extract().response();
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
		} catch (Exception e) {
			throw new AutomationException(e.getMessage());
		}
		responseInString = response.asString();
		return response;
	}

	/**
	 * Perform simple HTTP Get request
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param baseURI
	 * @param endPointPath
	 * @return
	 * @throws AutomationException
	 */
	public Response getRequest(String baseURI, String endPointPath) throws AutomationException {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).when().get(endPointPath);
			System.out.println("===================================================");
			System.out.println("Execution completed successfully");
		} catch (Exception e) {
			throw new AutomationException(e.getMessage());
		}
		return response;
	}

	/**
	 * Perform HTTP Get request with multiple query parameters
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param baseURI
	 * @param queryParams
	 * @param endPointPath
	 * @return
	 * @throws AutomationException
	 */
	public Response getRequest(String baseURI, Map<String, String> queryParams, String endPointPath)
			throws AutomationException {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).queryParams(queryParams).log().all().when()
					.get(endPointPath).andReturn();
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
			response.then().log().all();
		} catch (Exception e) {
			throw new AutomationException(e.getMessage());
		}
		return response;
	}

	/**
	 * Perform HTTP Get request with authorization token
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param baseURI
	 * @param endPointPath
	 * @param token
	 * @param pathParamName
	 * @param pathParamValue
	 * @return
	 * @throws AutomationException
	 */

	public Response getRequestWithAuthToken(String baseURI, String token, String pathParamName, String pathParamValue,
			String endPointPath) throws AutomationException {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).header("Authorization", "Bearer " + token)
					.pathParam(pathParamName, pathParamValue).log().all().when().get(endPointPath);
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
		} catch (Exception e) {
			throw new AutomationException(e.getMessage());
		}
		return response;
	}

	/**
	 * Perform simple HTTP Put request with body data from file
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param baseURI
	 * @param requestFilePath
	 * @param endPointPath
	 * @return
	 * @throws AutomationException
	 */
	public Response putRequest(String baseURI, String requestFilePath, String contentType, String endPointPath)
			throws AutomationException {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).body(new File(requestFilePath).getAbsoluteFile())
					.contentType(contentType).log().all().when().get(endPointPath).andReturn();
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
		} catch (Exception e) {
			throw new AutomationException(e.getMessage());
		}
		return response;
	}

	/**
	 * Perform HTTP Put request with query parameters and in-line body data
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param baseURI
	 * @param endPointPath
	 * @param body
	 * @param queryParams
	 * @return
	 * @throws AutomationException
	 */
	public Response putRequest(String baseURI, Map<String, String> body, Map<String, String> queryParams,
			String contentType, String endPointPath) throws AutomationException {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).queryParams(queryParams).body(body).contentType(contentType)
					.log().all().when().get(endPointPath).andReturn();
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
			response.then().log().all();
		} catch (Exception e) {
			throw new AutomationException(e.getMessage());
		}
		return response;
	}

	/**
	 * Perform simple HTTP Delete request
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param baseURI
	 * @param endPointPath
	 * @return
	 * @throws AutomationException
	 */
	public Response deleteRequest(String baseURI, String endPointPath) throws AutomationException {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).log().all().when().delete(endPointPath).andReturn();
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
			response.then().log().all();
		} catch (Exception e) {
			throw new AutomationException(e.getMessage());
		}
		return response;
	}

	/**
	 * Perform HTTP Delete request with multiple query parameters
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param baseURI
	 * @param endPointPath
	 * @param queryParams
	 * @return
	 * @throws AutomationException
	 */
	public Response deleteRequest(String baseURI, Map<String, String> queryParams, String endPointPath)
			throws AutomationException {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).queryParams(queryParams).log().all().when()
					.delete(endPointPath).andReturn();
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
			response.then().log().all();
		} catch (Exception e) {
			throw new AutomationException(e.getMessage());
		}
		responseInString = response.asString();
		return response;
	}
}
