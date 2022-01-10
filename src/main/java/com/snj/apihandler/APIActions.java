package com.snj.apihandler;

import java.io.File;
import java.util.Map;

import com.snj.exception.AutomationException;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class APIActions {

	public static String responseInString;

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

	public Response postRequest(String baseURI, File requestFilePath, String contentType, String endPointPath)
			throws AutomationException {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).body(requestFilePath).contentType(contentType).log().all()
					.when().post(endPointPath).andReturn();
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
	 * Perform HTTP Post request with in-line body data
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param baseURI
	 * @param endPointPath
	 * @param pathParams
	 * @param body
	 * @return
	 * @throws AutomationException
	 */
	public Response postRequest(String baseURI, String contentType, Map<String, String> pathParams,
			Map<String, String> body, String endPointPath) throws AutomationException {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).pathParams(pathParams).body(body).contentType(contentType)
					.log().all().when().post(endPointPath).andReturn();
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
		} catch (Exception e) {
			throw new AutomationException(e.getMessage());
		}
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
			response = RestAssured.given().baseUri(baseURI).log().all().when().get(endPointPath);
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
		} catch (Exception e) {
			throw new AutomationException(e.getMessage());
		}
		return response;
	}

	/**
	 * Perform HTTP Get request with path parameter
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param pathParamName
	 * @param pathParamValue
	 * @param endPointPath
	 * @return
	 * @throws AutomationException
	 */

	public Response getRequestWithPathParameter(String baseURI, String pathParamName, String pathParamValue,
			String endPointPath) throws AutomationException {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).pathParam(pathParamName, pathParamValue).log().all().when()
					.get(endPointPath);
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
		} catch (Exception e) {
			throw new AutomationException(e.getMessage());
		}
		return response;
	}

	/**
	 * Perform HTTP Get request with query parameter
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param baseURI
	 * @param queryParamName
	 * @param queryParamValue
	 * @param endPointPath
	 * @return
	 * @throws AutomationException
	 */
	public Response getRequestWithQueryParameter(String baseURI, String queryParamName, String queryParamValue,
			String endPointPath) throws AutomationException {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).queryParam(queryParamName, queryParamValue).log().all()
					.when().get(endPointPath).andReturn();
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
			response.then().log().all();
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
	public Response getRequestWithMultipleQueries(String baseURI, Map<String, String> queryParams, String endPointPath)
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
			response = RestAssured.given().baseUri(baseURI).body(requestFilePath).contentType(contentType).log().all()
					.when().get(endPointPath).andReturn();
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
		} catch (Exception e) {
			throw new AutomationException(e.getMessage());
		}
		return response;
	}

	/**
	 * Perform HTTP Put request with body data from file
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param baseURI
	 * @param requestFilePath
	 * @param pathParamName
	 * @param pathParamValue
	 * @param endPointPath
	 * @return
	 * @throws AutomationException
	 */
	public Response putRequest(String baseURI, File requestFilePath, String contentType, String pathParamName,
			String pathParamValue, String endPointPath) throws AutomationException {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).pathParam(pathParamName, pathParamValue)
					.body(requestFilePath).contentType(contentType).log().all().when().get(endPointPath).andReturn();
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
			response.then().log().all();
		} catch (Exception e) {
			throw new AutomationException(e.getMessage());
		}
		return response;
	}

	/**
	 * Perform HTTP Put request with in-line body data
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param baseURI
	 * @param endPointPath
	 * @param body
	 * @param pathParams
	 * @return
	 * @throws AutomationException
	 */
	public Response putRequest(String baseURI, Map<String, String> body, Map<String, String> pathParams,
			String contentType, String endPointPath) throws AutomationException {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).pathParams(pathParams).body(body).contentType(contentType)
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
	 * Perform HTTP Delete request with a given path parameter
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param baseURI
	 * @param pathParamName
	 * @param pathParamValue
	 * @param endPointPath
	 * @return
	 * @throws AutomationException
	 */
	public Response deleteRequestWithPathParameter(String baseURI, String pathParamName, String pathParamValue,
			String endPointPath) throws AutomationException {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).pathParam(pathParamName, pathParamValue).log().all().when()
					.delete(endPointPath).andReturn();
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
			response.then().log().all();
		} catch (Exception e) {
			throw new AutomationException(e.getMessage());
		}
		return response;
	}

	/**
	 * Perform HTTP Delete request with multiple path parameters
	 * 
	 * @author sanojs
	 * @since 15-04-2021
	 * @param baseURI
	 * @param endPointPath
	 * @param pathParams
	 * @return
	 * @throws AutomationException
	 */
	public Response deleteRequestWithMultiplePathParameters(String baseURI, Map<String, String> pathParams,
			String endPointPath) throws AutomationException {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).pathParams(pathParams).log().all().when()
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
