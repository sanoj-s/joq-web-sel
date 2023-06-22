package com.snj.keywords;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.http.HttpHeaders;
import org.json.JSONObject;

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
	 * @return @
	 */
	public Response postRequest(String baseURI, String endPointPath, String contentType,
			Map<String, String> queryParams, Map<String, String> body) {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).basePath(endPointPath).queryParams(queryParams).body(body)
					.contentType(contentType).log().all().when().post().andReturn();
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
		} catch (Exception e) {
			e.printStackTrace();
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
	 * @return @
	 */
	public Response postRequest(String baseURI, String endPointPath, String contentType,
			Map<String, String> queryParams, String payloadFileName) {
		Response response = null;
		try {
			String payloadPath = System.getProperty("user.dir") + AutomationConstants.API_REQUEST_PAYLOAD
					+ payloadFileName + ".json";
			response = RestAssured.given().baseUri(baseURI).basePath(endPointPath).queryParams(queryParams)
					.body(new File(payloadPath)).contentType(contentType).log().all().when().post().andReturn();
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
		} catch (Exception e) {
			e.printStackTrace();
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
	 * @return @
	 */

	public Response postRequest(String baseURI, String endPointPath, String payloadFileName, String contentType) {
		Response response = null;
		try {
			String payloadPath = System.getProperty("user.dir") + AutomationConstants.API_REQUEST_PAYLOAD
					+ payloadFileName + ".json";
			response = RestAssured.given().baseUri(baseURI).basePath(endPointPath)
					.body(new File(payloadPath).getAbsoluteFile()).contentType(contentType).log().all().when().post()
					.andReturn();
			response.then().log().all().extract().response();
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		responseInString = response.asString();
		return response;
	}

	/**
	 * Perform HTTP Post request with direct body data.
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-03-2023
	 * @param baseURI
	 * @param endPointPath
	 * @param requestBody
	 * @param contentType
	 * @return
	 * @throws AutomationException
	 */

	public Response postRequestWithBody(String baseURI, String endPointPath, String requestBody, String contentType)
			throws AutomationException {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).basePath(endPointPath).body(requestBody)
					.contentType(contentType).log().all().when().post().andReturn();
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
	 * Perform HTTP Post request with direct body data with token
	 * 
	 * @author sanoj.swaminathan
	 * @since 27-03-2023
	 * @param baseURI
	 * @param endPointPath
	 * @param requestBody
	 * @param authenticationScheme
	 * @param token
	 * @param contentType
	 * @return
	 * @throws AutomationException
	 */

	public Response postRequestWithBody(String baseURI, String endPointPath, String requestBody,
			String authenticationScheme, String token, String contentType) {
		Response response = null;
		try {
			response = RestAssured.given().header(HttpHeaders.AUTHORIZATION, authenticationScheme + " " + token)
					.contentType(contentType).body(requestBody).when().post(baseURI + endPointPath).then().log().all()
					.extract().response();

			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * Perform HTTP Post request with body data from file. You have to keep the JSON
	 * body data file in /src/test/resources/APITesting/RequestPayload/
	 * 
	 * @author sanoj.swaminathan
	 * @since 21-03-2023
	 * @param baseURI
	 * @param endPointPath
	 * @param payloadFilePath
	 * @param contentType
	 * @return @
	 */

	public Response postRequestWithPayloadPath(String baseURI, String endPointPath, String payloadFilePath,
			String contentType) {
		Response response = null;
		try {
			File payloadFile = new File(payloadFilePath);
			response = RestAssured.given().baseUri(baseURI).basePath(endPointPath).body(payloadFile.getAbsoluteFile())
					.contentType(contentType).log().all().when().post().andReturn();
			response.then().log().all().extract().response();
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		responseInString = response.asString();
		return response;
	}

	/**
	 * Perform HTTP Post request with direct GraphQL data
	 * 
	 * @author sanoj.swaminathan
	 * @since 29-05-2023
	 * @param baseURI
	 * @param endPointPath
	 * @param graphQLQuery
	 * @param contentType
	 * @return
	 * @throws AutomationException
	 */
	public Response postRequestWithGraphQL(String baseURI, String endPointPath, String graphQLQuery, String contentType)
			throws AutomationException {
		Response response = null;
		try {
			String requestPayload = graphqlToJSON(graphQLQuery);
			response = RestAssured.given().baseUri(baseURI).basePath(endPointPath).body(requestPayload)
					.contentType(contentType).log().all().when().post().andReturn();
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
	 * Perform HTTP Post request with GraphQL data from the file. The query text
	 * file should be placed in the GraphQL folder inside src/test/resources
	 * 
	 * @author sanoj.swaminathan
	 * @since 29-05-2023
	 * @param baseURI
	 * @param endPointPath
	 * @param graphQLQueryFileName
	 * @param contentType
	 * @return
	 * @throws AutomationException
	 */
	public Response postRequestWithGraphQLFile(String baseURI, String endPointPath, String graphQLQueryFileName,
			String contentType) throws AutomationException {
		Response response = null;
		try {
			String queryFilePath = System.getProperty("user.dir") + AutomationConstants.API_REQUEST_GRAPHQL
					+ graphQLQueryFileName + ".txt";
			String graphQLQuery = new String(Files.readAllBytes(Paths.get(queryFilePath)));
			String requestPayload = graphqlToJSON(graphQLQuery);
			response = RestAssured.given().baseUri(baseURI).basePath(endPointPath).body(requestPayload)
					.contentType(contentType).log().all().when().post().andReturn();
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
	 * Perform HTTP Post request with direct GraphQL data and variables
	 * 
	 * @author sanoj.swminathan
	 * @since 07-06-2023
	 * @param baseURI
	 * @param endPointPath
	 * @param graphQLQuery
	 * @param variable
	 * @param contentType
	 * @return
	 * @throws AutomationException
	 */
	public Response postRequestWithGraphQLWithVariable(String baseURI, String endPointPath, String graphQLQuery,
			String variable, String contentType) throws AutomationException {
		Response response = null;
		try {
			String requestPayload = graphqlToJSON(graphQLQuery, variable);
			response = RestAssured.given().baseUri(baseURI).basePath(endPointPath).body(requestPayload)
					.contentType(contentType).log().all().when().post().andReturn();
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
	 * Perform HTTP Post request with GraphQL data from the given file path. The
	 * file format should be in .txt
	 * 
	 * @author sanoj.swaminathan
	 * @since 29-05-2023
	 * @param baseURI
	 * @param endPointPath
	 * @param graphQLQueryFilePath
	 * @param contentType
	 * @return
	 * @throws AutomationException
	 */
	public Response postRequestWithGraphQLFilePath(String baseURI, String endPointPath, String graphQLQueryFilePath,
			String contentType) throws AutomationException {
		Response response = null;
		try {
			String graphQLQuery = new String(Files.readAllBytes(Paths.get(graphQLQueryFilePath)));
			String requestPayload = graphqlToJSON(graphQLQuery);
			response = RestAssured.given().baseUri(baseURI).basePath(endPointPath).body(requestPayload)
					.contentType(contentType).log().all().when().post().andReturn();
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
	 * Method to convert GraphQL to JSON
	 * 
	 * @author sanoj.swaminathan
	 * @since 29-05-2023
	 * @param graphQLQuery
	 * @return
	 * @throws AutomationException
	 */
	private String graphqlToJSON(String graphQLQuery) throws AutomationException {
		JSONObject json;
		try {
			json = new JSONObject();
			json.put("query", graphQLQuery);

		} catch (Exception e) {
			throw new AutomationException(e.getMessage());
		}
		return json.toString();
	}

	/**
	 * Method to convert GraphQL to JSON with variable
	 * 
	 * @author sanoj.swaminathan
	 * @since 07-06-2023
	 * @param graphQLQuery
	 * @return
	 * @throws AutomationException
	 */
	private String graphqlToJSON(String graphQLQuery, String variables) throws AutomationException {
		JSONObject json;
		try {
			json = new JSONObject();
			json.put("query", graphQLQuery);
			json.put("variables", variables);
		} catch (Exception e) {
			throw new AutomationException(e.getMessage());
		}
		return json.toString();
	}

	/**
	 * Perform simple HTTP Get request
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-04-2021
	 * @param baseURI
	 * @param endPointPath
	 * @return @
	 */
	public Response getRequest(String baseURI, String endPointPath) {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).basePath(endPointPath).when().get();
			System.out.println("===================================================");
			System.out.println("Execution completed successfully");
		} catch (Exception e) {
			e.printStackTrace();
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
	 * @return @
	 */
	public Response getRequest(String baseURI, String endPointPath, Map<String, String> queryParams) {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).basePath(endPointPath).queryParams(queryParams).log().all()
					.when().get().andReturn();
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
			response.then().log().all();
		} catch (Exception e) {
			e.printStackTrace();
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
	 * @param authenticationScheme
	 * @param token
	 * @param pathParamName
	 * @param pathParamValue
	 * @return @
	 */

	public Response getRequestWithAuthToken(String baseURI, String endPointPath, String authenticationScheme,
			String token, String pathParamName, String pathParamValue) {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).basePath(endPointPath)
					.header("Authorization", authenticationScheme + " " + token)
					.pathParam(pathParamName, pathParamValue).log().all().when().get();
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
		} catch (Exception e) {
			e.printStackTrace();
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
	 * @param authenticationScheme
	 * @param token
	 * @return @
	 */

	public Response getRequestWithAuthToken(String baseURI, String endPointPath, String authenticationScheme,
			String token) {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).basePath(endPointPath)
					.header(HttpHeaders.AUTHORIZATION, authenticationScheme + " " + token).when().get();

			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
		} catch (Exception e) {
			e.printStackTrace();
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
	 * @return @
	 */
	public Response putRequest(String baseURI, String endPointPath, String payloadFileName, String contentType) {
		Response response = null;
		try {
			String payloadPath = System.getProperty("user.dir") + AutomationConstants.API_REQUEST_PAYLOAD
					+ payloadFileName + ".json";
			response = RestAssured.given().baseUri(baseURI).basePath(endPointPath)
					.body(new File(payloadPath).getAbsoluteFile()).contentType(contentType).log().all().when().put()
					.andReturn();
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
		} catch (Exception e) {
			e.printStackTrace();
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
	 * @return @
	 */
	public Response putRequest(String baseURI, String endPointPath, Map<String, String> body,
			Map<String, String> queryParams, String contentType) {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).basePath(endPointPath).queryParams(queryParams).body(body)
					.contentType(contentType).log().all().when().put().andReturn();
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
			response.then().log().all();
		} catch (Exception e) {
			e.printStackTrace();
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
	 * @return @
	 */
	public Response patchRequest(String baseURI, String endPointPath, String payloadFileName, String contentType) {
		Response response = null;
		try {
			String payloadPath = System.getProperty("user.dir") + AutomationConstants.API_REQUEST_PAYLOAD
					+ payloadFileName + ".json";
			response = RestAssured.given().baseUri(baseURI).basePath(endPointPath)
					.body(new File(payloadPath).getAbsoluteFile()).contentType(contentType).log().all().when().patch()
					.andReturn();
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * Perform HTTP Patch request with direct body data.
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-03-2023
	 * @param baseURI
	 * @param endPointPath
	 * @param requestBody
	 * @param contentType
	 * @return
	 * @throws AutomationException
	 */

	public Response patchRequestWithBody(String baseURI, String endPointPath, String requestBody, String contentType)
			throws AutomationException {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).basePath(endPointPath).body(requestBody)
					.contentType(contentType).log().all().when().patch().andReturn();
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
	 * Perform HTTP Patch request with direct body data with token.
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-03-2023
	 * @param baseURI
	 * @param endPointPath
	 * @param requestBody
	 * @param authenticationScheme
	 * @param token
	 * @param contentType
	 * @return
	 * @throws AutomationException
	 */

	public Response patchRequestWithBody(String baseURI, String endPointPath, String requestBody,
			String authenticationScheme, String token, String contentType) throws AutomationException {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).basePath(endPointPath)
					.header(HttpHeaders.AUTHORIZATION, authenticationScheme + " " + token).body(requestBody)
					.contentType(contentType).log().all().when().patch().andReturn();
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
	 * Perform HTTP Patch request with query parameters and in-line body data
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-04-2021
	 * @param baseURI
	 * @param endPointPath
	 * @param body
	 * @param queryParams
	 * @param contentType
	 * @return @
	 */
	public Response patchRequest(String baseURI, String endPointPath, Map<String, String> body,
			Map<String, String> queryParams, String contentType) {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).basePath(endPointPath).queryParams(queryParams).body(body)
					.contentType(contentType).log().all().when().patch().andReturn();
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
			response.then().log().all();
		} catch (Exception e) {
			e.printStackTrace();
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
	 * @return @
	 */
	public Response deleteRequest(String baseURI, String endPointPath) {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).basePath(endPointPath).log().all().when().delete()
					.andReturn();
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
			response.then().log().all();
		} catch (Exception e) {
			e.printStackTrace();
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
	 * @return @
	 */
	public Response deleteRequest(String baseURI, String endPointPath, Map<String, String> queryParams) {
		Response response = null;
		try {
			response = RestAssured.given().baseUri(baseURI).basePath(endPointPath).queryParams(queryParams).log().all()
					.when().delete().andReturn();
			System.out.println("==========================================================");
			System.out.println("Execution completed successfully");
			response.then().log().all();
		} catch (Exception e) {
			e.printStackTrace();
			;
		}
		responseInString = response.asString();
		return response;
	}
}
