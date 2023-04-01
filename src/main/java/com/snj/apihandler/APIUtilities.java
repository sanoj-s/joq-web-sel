package com.snj.apihandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.snj.exception.AutomationException;
import com.snj.keywords.Utilities;
import com.snj.utils.AutomationConstants;
import com.snj.utils.ResponseTimeTracker;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class APIUtilities {

	/**
	 * Get response time. The timeUnitIn can be seconds/milliseconds/minutes.
	 * 
	 * @author sanoj.swaminathan
	 * @since 01-04-2023
	 * @param responseData
	 * @param timeUnitIn
	 * @return
	 * @throws AutomationException
	 */
	public int getResponseTime(Response responseData, String timeUnitIn) throws AutomationException {
		long responseTime = 0;
		try {
			switch (timeUnitIn.toLowerCase()) {
			case "milliseconds":
				responseTime = responseData.getTimeIn(TimeUnit.MILLISECONDS);
				break;
			case "seconds":
				responseTime = responseData.getTimeIn(TimeUnit.SECONDS);
				break;
			case "minutes":
				responseTime = responseData.getTimeIn(TimeUnit.MINUTES);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (int) responseTime;
	}

	/**
	 * Get response code
	 * 
	 * @author sanoj.swaminathan
	 * @since 07-03-2023
	 * @modified 31-03-2023
	 * @param responseData
	 * @return
	 * @throws AutomationException
	 */
	public int getResponseCode(Response responseData) throws AutomationException {
		int responseCode = 0;
		try {
			responseCode = responseData.getStatusCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseCode;
	}

	/**
	 * Get the value from JSON response
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-04-2021
	 * @modified 31-03-2023
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
			try {
				JsonPath jsonPath = new JsonPath(responseData.asPrettyString());
				jsonValue = new Utilities().convertIntToString(jsonPath.getInt(parameterName));
			} catch (Exception ex) {
				System.out.println("Failed to get the value from the response");
			}
		}
		return jsonValue;
	}

	/**
	 * Method to get the array size from the response for the given array path
	 * 
	 * @author sanoj.swaminathan
	 * @since 27-03-2023
	 * @modified 31-03-2023
	 * @param response
	 * @param pathToArray
	 * @return
	 */
	public int getArraySize(Response response, String pathToArray) {
		int metadataArraySize = 0;
		try {
			metadataArraySize = new APIUtilities().getValueFromResponse(response, pathToArray + ".size()").length();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return metadataArraySize;
	}

	/**
	 * Get array values from JSON file - specify the response JSON file in
	 * /src/test/resources/APITesting/ResponseData/ folder and pass the JSONFileName
	 * 
	 * @author sanoj.swaminathan
	 * @since 07-02-2022
	 * @modified 31-03-2023
	 * @param JSONFileName
	 * @param arrayName
	 * @param keyName
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	public ArrayList<Object> getArrayValuesFromJSONFile(String JSONFileName, String arrayName, String keyName)
			throws FileNotFoundException, IOException, ParseException {
		Object value = null;
		ArrayList<Object> listValue = new ArrayList<>();
		JSONParser jsonParser = new JSONParser();
		String responseDataPath = System.getProperty("user.dir") + AutomationConstants.API_RESPONSE_DATA + JSONFileName
				+ ".json";
		JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(responseDataPath));
		JSONArray jsonArray = (JSONArray) jsonObject.get(arrayName);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonobject = (JSONObject) jsonArray.get(i);
			value = jsonobject.get(keyName);
			if (((Object) value).getClass().getSimpleName().equalsIgnoreCase("string")) {
				value = new String(((String) value).getBytes("ISO-8859-1"), "UTF-8");
			}
			listValue.add(value);
		}
		return listValue;
	}

	/**
	 * Method to update the JSONObject data based on the given JSON file path
	 * 
	 * @author sanoj.swaminathan
	 * @since 23-03-2023
	 * @param JSONFilePath
	 * @param JSONObjects
	 * @param keyName
	 * @param keyValue
	 * @return
	 * @throws AutomationException
	 * @throws IOException
	 */
	public String updateJSONDataWithGivenFilePath(String JSONFilePath, List<String> JSONObjects, String keyName,
			String keyValue) throws AutomationException, IOException {
		int sizeOfJSONObject;
		JsonNode objectNode = null;
		try {
			if (JSONObjects == null) {
				sizeOfJSONObject = 0;
			} else {
				sizeOfJSONObject = JSONObjects.size();
			}
			ObjectMapper objMapper = new ObjectMapper();
			File file = new File(JSONFilePath);
			JsonNode jsonNode = objMapper.readTree(file);
			System.out.println("Original JSON: " + jsonNode.toString());
			switch (sizeOfJSONObject) {
			case 0:
				objectNode = jsonNode;
				break;
			case 1:
				objectNode = jsonNode.get(JSONObjects.get(0));
				break;
			case 2:
				objectNode = jsonNode.get(JSONObjects.get(0)).get(JSONObjects.get(1));
				break;
			case 3:
				objectNode = jsonNode.get(JSONObjects.get(0)).get(JSONObjects.get(1)).get(JSONObjects.get(2));
				break;
			case 4:
				objectNode = jsonNode.get(JSONObjects.get(0)).get(JSONObjects.get(1)).get(JSONObjects.get(2))
						.get(JSONObjects.get(3));
				break;
			default:
				break;
			}
			((ObjectNode) objectNode).put(keyName, keyValue);
			System.out.println("Updated JSON: " + jsonNode.toString());
			try (FileWriter files = new FileWriter(JSONFilePath)) {
				String json = objMapper.writeValueAsString(jsonNode);
				files.write(json);
				files.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONFilePath;
	}

	/**
	 * Method to update the JSONObject data based on the given JSON file name, the
	 * file should be kept inside the /src/test/resources/APITesting/RequestPayload/
	 * 
	 * @author sanoj.swaminathan
	 * @since 23-03-2023
	 * @param JSONFileName
	 * @param JSONObjects
	 * @param keyName
	 * @param keyValue
	 * @return
	 * @throws AutomationException
	 * @throws IOException
	 */
	public String updateJSONDataWithGivenFileName(String JSONFileName, List<String> JSONObjects, String keyName,
			String keyValue) throws AutomationException, IOException {
		int sizeOfJSONObject;
		String payloadPath = null;
		JsonNode objectNode = null;
		try {
			if (JSONObjects == null) {
				sizeOfJSONObject = 0;
			} else {
				sizeOfJSONObject = JSONObjects.size();
			}
			payloadPath = System.getProperty("user.dir") + AutomationConstants.API_REQUEST_PAYLOAD + JSONFileName
					+ ".json";
			File file = new File(payloadPath);
			ObjectMapper objMapper = new ObjectMapper();
			JsonNode jsonNode = objMapper.readTree(file);
			System.out.println("Original JSON: " + jsonNode.toString());
			switch (sizeOfJSONObject) {
			case 0:
				objectNode = jsonNode;
				break;
			case 1:
				objectNode = jsonNode.get(JSONObjects.get(0));
				break;
			case 2:
				objectNode = jsonNode.get(JSONObjects.get(0)).get(JSONObjects.get(1));
				break;
			case 3:
				objectNode = jsonNode.get(JSONObjects.get(0)).get(JSONObjects.get(1)).get(JSONObjects.get(2));
				break;
			case 4:
				objectNode = jsonNode.get(JSONObjects.get(0)).get(JSONObjects.get(1)).get(JSONObjects.get(2))
						.get(JSONObjects.get(3));
				break;
			default:
				break;
			}
			((ObjectNode) objectNode).put(keyName, keyValue);
			System.out.println("Updated JSON: " + jsonNode.toString());
			try (FileWriter files = new FileWriter(payloadPath)) {
				String json = objMapper.writeValueAsString(jsonNode);
				files.write(json);
				files.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return payloadPath;
	}

	/**
	 * Method to update the JSONObject data on the given response data after that
	 * the payload file will be generated at
	 * /src/test/resources/APITesting/RequestPayload/ location
	 * 
	 * @author sanoj.swaminathan
	 * @since 23-03-2023
	 * @param response
	 * @param JSONObjects
	 * @param keyName
	 * @param keyValue
	 * @return
	 * @throws AutomationException
	 * @throws IOException
	 */
	public String updateJSONDataInResponse(Response response, List<String> JSONObjects, String keyName, String keyValue)
			throws AutomationException, IOException {
		int sizeOfJSONObject;
		JsonNode objectNode = null;
		String payloadPath = null;
		try {
			if (JSONObjects == null) {
				sizeOfJSONObject = 0;
			} else {
				sizeOfJSONObject = JSONObjects.size();
			}
			ObjectMapper objMapper = new ObjectMapper();
			JsonNode jsonNode = objMapper.readTree(response.asPrettyString());
			System.out.println("Original JSON: " + jsonNode.toString());
			switch (sizeOfJSONObject) {
			case 0:
				objectNode = jsonNode;
				break;
			case 1:
				objectNode = jsonNode.get(JSONObjects.get(0));
				break;
			case 2:
				objectNode = jsonNode.get(JSONObjects.get(0)).get(JSONObjects.get(1));
				break;
			case 3:
				objectNode = jsonNode.get(JSONObjects.get(0)).get(JSONObjects.get(1)).get(JSONObjects.get(2));
				break;
			case 4:
				objectNode = jsonNode.get(JSONObjects.get(0)).get(JSONObjects.get(1)).get(JSONObjects.get(2))
						.get(JSONObjects.get(3));
				break;
			default:
				break;
			}
			((ObjectNode) objectNode).put(keyName, keyValue);
			System.out.println("Updated JSON: " + jsonNode.toString());
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss", Locale.getDefault());
			LocalDateTime dateNow = LocalDateTime.now();
			String dateValue = dateFormat.format(dateNow);
			payloadPath = System.getProperty("user.dir") + AutomationConstants.API_REQUEST_PAYLOAD + "payload" + "_"
					+ dateValue + ".json";
			try (FileWriter files = new FileWriter(payloadPath)) {
				String json = objMapper.writeValueAsString(jsonNode);
				files.write(json);
				files.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return payloadPath;
	}

	/**
	 * Method to update the JSONObject array data based on the given JSON file path
	 * and array index
	 * 
	 * @author sanoj.swaminathan
	 * @since 23-03-2023
	 * @param JSONFilePath
	 * @param list
	 * @param arrayIndex
	 * @param keyName
	 * @param keyValue
	 * @return
	 * @throws IOException
	 * @throws AutomationException
	 */
	public String updateJSONArrayDataWithGivenFilePath(String JSONFilePath, List<String> list, int arrayIndex,
			String keyName, String keyValue) throws IOException, AutomationException {
		try {
			ObjectMapper objMapper = new ObjectMapper();
			File file = new File(JSONFilePath);
			JsonNode jsonNode = objMapper.readTree(file);
			System.out.println("Original JSON: " + jsonNode.toString());
			JsonNode objectNode = getObjectNode(jsonNode, list);
			ArrayNode arrayNode = (ArrayNode) objectNode;
			if (arrayNode.isArray()) {
				((ObjectNode) arrayNode.get(arrayIndex - 1)).put(keyName, keyValue);
			}
			System.out.println("Updated JSON: " + jsonNode.toString());
			try (FileWriter files = new FileWriter(JSONFilePath)) {
				String json = objMapper.writeValueAsString(jsonNode);
				files.write(json);
				files.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONFilePath;
	}

	/**
	 * Method to update the JSONObject array data based on the given JSON file name
	 * and the array index; the file should be kept inside the
	 * /src/test/resources/APITesting/RequestPayload/
	 * 
	 * @author sanoj.swaminathan
	 * @since 23-03-2023
	 * @param JSONFileName
	 * @param list
	 * @param arrayIndex
	 * @param keyName
	 * @param keyValue
	 * @return
	 * @throws IOException
	 * @throws AutomationException
	 */
	public String updateJSONArrayDataWithGivenFileName(String JSONFileName, List<String> list, int arrayIndex,
			String keyName, String keyValue) throws IOException, AutomationException {
		String payloadPath = null;
		try {
			ObjectMapper objMapper = new ObjectMapper();
			payloadPath = System.getProperty("user.dir") + AutomationConstants.API_REQUEST_PAYLOAD + JSONFileName
					+ ".json";
			File file = new File(payloadPath);
			JsonNode jsonNode = objMapper.readTree(file);
			System.out.println("Original JSON: " + jsonNode.toString());
			JsonNode objectNode = getObjectNode(jsonNode, list);
			ArrayNode arrayNode = (ArrayNode) objectNode;
			if (arrayNode.isArray()) {
				((ObjectNode) arrayNode.get(arrayIndex - 1)).put(keyName, keyValue);
			}
			System.out.println("Updated JSON: " + jsonNode.toString());
			try (FileWriter files = new FileWriter(payloadPath)) {
				String json = objMapper.writeValueAsString(jsonNode);
				files.write(json);
				files.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return payloadPath;
	}

	/**
	 * Method to update the JSONObject array data on the given response data and
	 * array index; after that the payload file will be generated at
	 * /src/test/resources/APITesting/RequestPayload/ location
	 * 
	 * @author sanoj.swaminathan
	 * @since 23-03-2023
	 * @param response
	 * @param list
	 * @param arrayIndex
	 * @param keyName
	 * @param keyValue
	 * @return
	 * @throws IOException
	 * @throws AutomationException
	 */
	public String updateJSONDataInResponse(Response response, List<String> list, int arrayIndex, String keyName,
			String keyValue) throws IOException, AutomationException {
		String payloadPath = null;
		try {
			ObjectMapper objMapper = new ObjectMapper();
			JsonNode jsonNode = objMapper.readTree(response.asPrettyString());
			System.out.println("Original JSON: " + jsonNode.toString());
			JsonNode objectNode = getObjectNode(jsonNode, list);
			ArrayNode arrayNode = (ArrayNode) objectNode;
			if (arrayNode.isArray()) {
				((ObjectNode) arrayNode.get(arrayIndex - 1)).put(keyName, keyValue);
			}
			System.out.println("Updated JSON: " + jsonNode.toString());
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss", Locale.getDefault());
			LocalDateTime dateNow = LocalDateTime.now();
			String dateValue = dateFormat.format(dateNow);
			payloadPath = System.getProperty("user.dir") + AutomationConstants.API_REQUEST_PAYLOAD + "payload" + "_"
					+ dateValue + ".json";
			try (FileWriter files = new FileWriter(payloadPath)) {
				String json = objMapper.writeValueAsString(jsonNode);
				files.write(json);
				files.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return payloadPath;
	}

	/**
	 * Method to update the JSONObject array data based on the given JSON file path,
	 * array name and array index
	 * 
	 * @author sanoj.swaminathan
	 * @since 23-03-2023
	 * @param JSONFilePath
	 * @param list
	 * @param arrayName
	 * @param arrayIndex
	 * @param keyName
	 * @param keyValue
	 * @return
	 * @throws IOException
	 * @throws AutomationException
	 */
	public String updateJSONArrayDataWithGivenFilePath(String JSONFilePath, List<String> list, String arrayName,
			int arrayIndex, String keyName, String keyValue) throws IOException, AutomationException {
		try {
			ObjectMapper objMapper = new ObjectMapper();
			File file = new File(JSONFilePath);
			JsonNode jsonNode = objMapper.readTree(file);
			System.out.println("Original JSON: " + jsonNode.toString());
			JsonNode objectNode = getObjectNode(jsonNode, list);
			ArrayNode arrayNode = (ArrayNode) objectNode.get(arrayName);
			if (arrayNode.isArray()) {
				((ObjectNode) arrayNode.get(arrayIndex - 1)).put(keyName, keyValue);
			}
			System.out.println("Updated JSON: " + jsonNode.toString());
			try (FileWriter files = new FileWriter(JSONFilePath)) {
				String json = objMapper.writeValueAsString(jsonNode);
				files.write(json);
				files.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONFilePath;
	}

	/**
	 * Method to update the JSONObject array data based on the given JSON file name,
	 * array name and the array index; the file should be kept inside the
	 * /src/test/resources/APITesting/RequestPayload/
	 * 
	 * @author sanoj.swaminathan
	 * @since 23-03-2023
	 * @param JSONFileName
	 * @param list
	 * @param arrayName
	 * @param arrayIndex
	 * @param keyName
	 * @param keyValue
	 * @return
	 * @throws IOException
	 * @throws AutomationException
	 */
	public String updateJSONArrayDataWithGivenFileName(String JSONFileName, List<String> list, String arrayName,
			int arrayIndex, String keyName, String keyValue) throws IOException, AutomationException {
		String payloadPath = null;
		try {
			ObjectMapper objMapper = new ObjectMapper();
			payloadPath = System.getProperty("user.dir") + AutomationConstants.API_REQUEST_PAYLOAD + JSONFileName
					+ ".json";
			File file = new File(payloadPath);
			JsonNode jsonNode = objMapper.readTree(file);
			System.out.println("Original JSON: " + jsonNode.toString());
			JsonNode objectNode = getObjectNode(jsonNode, list);
			ArrayNode arrayNode = (ArrayNode) objectNode.get(arrayName);
			if (arrayNode.isArray()) {
				((ObjectNode) arrayNode.get(arrayIndex - 1)).put(keyName, keyValue);
			}
			System.out.println("Updated JSON: " + jsonNode.toString());
			try (FileWriter files = new FileWriter(payloadPath)) {
				String json = objMapper.writeValueAsString(jsonNode);
				files.write(json);
				files.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return payloadPath;
	}

	/**
	 * Method to update the JSONObject array data on the given response data, array
	 * name and array index; after that the payload file will be generated at
	 * /src/test/resources/APITesting/RequestPayload/ location
	 * 
	 * @author sanoj.swaminathan
	 * @since 23-03-2023
	 * @param response
	 * @param list
	 * @param arrayName
	 * @param arrayIndex
	 * @param keyName
	 * @param keyValue
	 * @return
	 * @throws IOException
	 * @throws AutomationException
	 */
	public String updateJSONDataInResponse(Response response, List<String> list, String arrayName, int arrayIndex,
			String keyName, String keyValue) throws IOException, AutomationException {
		String payloadPath = null;
		try {
			ObjectMapper objMapper = new ObjectMapper();
			JsonNode jsonNode = objMapper.readTree(response.asPrettyString());
			System.out.println("Original JSON: " + jsonNode.toString());
			JsonNode objectNode = getObjectNode(jsonNode, list);
			ArrayNode arrayNode = (ArrayNode) objectNode.get(arrayName);
			if (arrayNode.isArray()) {
				((ObjectNode) arrayNode.get(arrayIndex - 1)).put(keyName, keyValue);
			}
			System.out.println("Updated JSON: " + jsonNode.toString());
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss", Locale.getDefault());
			LocalDateTime dateNow = LocalDateTime.now();
			String dateValue = dateFormat.format(dateNow);
			payloadPath = System.getProperty("user.dir") + AutomationConstants.API_REQUEST_PAYLOAD + "payload" + "_"
					+ dateValue + ".json";
			try (FileWriter files = new FileWriter(payloadPath)) {
				String json = objMapper.writeValueAsString(jsonNode);
				files.write(json);
				files.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return payloadPath;
	}

	/**
	 * Method to get the JSON node value based on given JSON object list
	 * 
	 * @author sanoj.swaminathan
	 * @since 23-03-2023
	 * @param jsonNode
	 * @param JSONObjects
	 * @return
	 * @throws AutomationException
	 * @throws IOException
	 */
	private JsonNode getObjectNode(JsonNode jsonNode, List<String> JSONObjects)
			throws AutomationException, IOException {
		int sizeOfJSONObject;
		if (JSONObjects == null) {
			sizeOfJSONObject = 0;
		} else {
			sizeOfJSONObject = JSONObjects.size();
		}
		JsonNode objectNode = null;
		switch (sizeOfJSONObject) {
		case 0:
			objectNode = jsonNode;
			break;
		case 1:
			objectNode = jsonNode.get(JSONObjects.get(0));
			break;
		case 2:
			objectNode = jsonNode.get(JSONObjects.get(0)).get(JSONObjects.get(1));
			break;
		case 3:
			objectNode = jsonNode.get(JSONObjects.get(0)).get(JSONObjects.get(1)).get(JSONObjects.get(2));
			break;
		case 4:
			objectNode = jsonNode.get(JSONObjects.get(0)).get(JSONObjects.get(1)).get(JSONObjects.get(2))
					.get(JSONObjects.get(3));
			break;
		default:
			break;
		}
		return objectNode;
	}

	/**
	 * Collect the API response time during automation execution. Once the execution
	 * completes the report will be available at API_Response_Time_Audit folder
	 * inside the Reports folder
	 * 
	 * @author sanoj.swaminathan
	 * @since 01-04-2023
	 * @param response
	 * @param expectedTime
	 * @param APIName
	 * @param reportName
	 * @throws AutomationException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void collectResponseTime(Response response, long expectedTime, String APIName, String reportName)
			throws AutomationException, IOException, InterruptedException {
		try {
			new ResponseTimeTracker().trackResponseTime(response, expectedTime, APIName, reportName);
		} catch (Exception e) {
		}
	}
}
