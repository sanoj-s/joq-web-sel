package com.snj.apihandler;

import java.io.File;
import java.util.Map;

import com.snj.exception.AutomationException;
import com.snj.utils.AutomationConstants;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class APIActions {

	public static String responseInString;

	/**
	 * Perform HTTP Post request with in-line body data
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-04-2021
	 * @param baseURI
	 * @param endPointPath
	 * @param contentType
	 * @param queryParams
	 * @param body
	 * @return
	 * @throws AutomationException
	 */
	public Response postRequest(String baseURI, String endPointPath, String contentType,
			Map<String, String> queryParams, Map<String, String> body) throws AutomationException {
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
	 * Perform HTTP Post request with body data as JSON file
	 * 
	 * @author sanoj.swaminathan
	 * @since 14-07-2022
	 * @param baseURI
	 * @param endPointPath
	 * @param contentType
	 * @param queryParams
	 * @param payloadFileName
	 * @return
	 * @throws AutomationException
	 */
	public Response postRequest(String baseURI, String endPointPath, String contentType,
			Map<String, String> queryParams, String payloadFileName) throws AutomationException {
		Response response = null;
		try {
			String payloadPath = System.getProperty("user.dir") + AutomationConstants.API_REQUEST_PAYLOAD
					+ payloadFileName + ".json";
			response = RestAssured.given().baseUri(baseURI).queryParams(queryParams).body(new File(payloadPath))
					.contentType(contentType).log().all().when().post(endPointPath).andReturn();
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
		} catch (Exception e) {
			throw new AutomationException(e.getMessage());
		}
		return response;
	}

	/**
	 * Perform HTTP Post request with body data from file. You have to keep the JSON
	 * body data file in /src/test/resources/APITesting/RequestPayload/
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-04-2021
	 * @modified 21-02-2023
	 * @param baseURI
	 * @param endPointPath
	 * @param payloadFileName
	 * @param contentType
	 * @return
	 * @throws AutomationException
	 */

	public Response postRequest(String baseURI, String endPointPath, String payloadFileName, String contentType)
			throws AutomationException {
		Response response = null;
		try {
			String payloadPath = System.getProperty("user.dir") + AutomationConstants.API_REQUEST_PAYLOAD
					+ payloadFileName + ".json";
			response = RestAssured.given().baseUri(baseURI).body(new File(payloadPath).getAbsoluteFile())
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
	 * Perform HTTP Post request with body data from file.
	 * 
	 * @author sanoj.swaminathan
	 * @since 21-03-2023
	 * @param baseURI
	 * @param endPointPath
	 * @param payloadFilePath
	 * @param contentType
	 * @return
	 * @throws AutomationException
	 */

	public Response postRequestWithPayloadPath(String baseURI, String endPointPath, String payloadFilePath,
			String contentType) throws AutomationException {
		Response response = null;
		try {
			File payloadFile = new File(payloadFilePath);
			response = RestAssured.given().baseUri(baseURI).body(payloadFile.getAbsoluteFile()).contentType(contentType)
					.log().all().when().post(endPointPath).andReturn();
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
	 * @author sanoj.swaminathan
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
	 * @author sanoj.swaminathan
	 * @since 15-04-2021
	 * @param baseURI
	 * @param endPointPath
	 * @param queryParams
	 * @return
	 * @throws AutomationException
	 */
	public Response getRequest(String baseURI, String endPointPath, Map<String, String> queryParams)
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
	 * @author sanoj.swaminathan
	 * @since 15-04-2021
	 * @param baseURI
	 * @param endPointPath
	 * @param token
	 * @param pathParamName
	 * @param pathParamValue
	 * @return
	 * @throws AutomationException
	 */

	public Response getRequestWithAuthToken(String baseURI, String endPointPath, String token, String pathParamName,
			String pathParamValue) throws AutomationException {
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
	 * @author sanoj.swaminathan
	 * @since 15-04-2021
	 * @param baseURI
	 * @param endPointPath
	 * @param payloadFileName
	 * @param contentType
	 * @return
	 * @throws AutomationException
	 */
	public Response putRequest(String baseURI, String endPointPath, String payloadFileName, String contentType)
			throws AutomationException {
		Response response = null;
		try {
			String payloadPath = System.getProperty("user.dir") + AutomationConstants.API_REQUEST_PAYLOAD
					+ payloadFileName + ".json";
			response = RestAssured.given().baseUri(baseURI).body(new File(payloadPath).getAbsoluteFile())
					.contentType(contentType).log().all().when().put(endPointPath).andReturn();
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
	 * @author sanoj.swaminathan
	 * @since 15-04-2021
	 * @param baseURI
	 * @param endPointPath
	 * @param body
	 * @param queryParams
	 * @param contentType
	 * @return
	 * @throws AutomationException
	 */
	public Response putRequest(String baseURI, String endPointPath, Map<String, String> body,
			Map<String, String> queryParams, String contentType) throws AutomationException {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).queryParams(queryParams).body(body).contentType(contentType)
					.log().all().when().put(endPointPath).andReturn();
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
			response.then().log().all();
		} catch (Exception e) {
			throw new AutomationException(e.getMessage());
		}
		return response;
	}

	/**
	 * Perform simple HTTP Patch request with body data from file
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-04-2021
	 * @param baseURI
	 * @param endPointPath
	 * @param payloadFileName
	 * @param contentType
	 * @return
	 * @throws AutomationException
	 */
	public Response patchRequest(String baseURI, String endPointPath, String payloadFileName, String contentType)
			throws AutomationException {
		Response response = null;
		try {
			String payloadPath = System.getProperty("user.dir") + AutomationConstants.API_REQUEST_PAYLOAD
					+ payloadFileName + ".json";
			response = RestAssured.given().baseUri(baseURI).body(new File(payloadPath).getAbsoluteFile())
					.contentType(contentType).log().all().when().patch(endPointPath).andReturn();
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
		} catch (Exception e) {
			throw new AutomationException(e.getMessage());
		}
		return response;
	}

	/**
	 * Perform HTTP Patch request with query parameters and in-line body data
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-04-2021
	 * @param baseURI
	 * @param endPointPath
	 * @param body
	 * @param queryParams
	 * @param contentType
	 * @return
	 * @throws AutomationException
	 */
	public Response patchRequest(String baseURI, String endPointPath, Map<String, String> body,
			Map<String, String> queryParams, String contentType) throws AutomationException {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).queryParams(queryParams).body(body).contentType(contentType)
					.log().all().when().patch(endPointPath).andReturn();
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
	 * @author sanoj.swaminathan
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
	 * @author sanoj.swaminathan
	 * @since 15-04-2021
	 * @param baseURI
	 * @param endPointPath
	 * @param queryParams
	 * @return
	 * @throws AutomationException
	 */
	public Response deleteRequest(String baseURI, String endPointPath, Map<String, String> queryParams)
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
