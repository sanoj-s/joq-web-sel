package snj.mars.testcases;

import java.io.IOException;
import java.util.ArrayList;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.snj.exception.AutomationException;
import com.snj.keywords.APIActions;
import com.snj.keywords.APIUtilities;
import com.snj.keywords.APIValidations;
import com.snj.keywords.Validations;
import com.snj.reporting.AutomationReport;

import io.restassured.response.Response;

@Listeners(AutomationReport.class)
public class SampleAPITests {
	String baseURI;

	@BeforeClass
	public void setup() {
		baseURI = "https://reqres.in";
	}

	@Test(enabled = true, description = "Retrieve user")
	public void TC001_testGetAPIRequest() throws AutomationException {
		new AutomationReport().assignCategory("GET Request");

		Response resp = new APIActions().getRequest(baseURI, "/api/users/2");

		// Validate the response data against the expected JSON file data.
		new APIValidations().validateJsonResponseMatch(resp, "user_payload");

		new APIValidations().validateStatusCode(resp, 200);
		new AutomationReport().trackSteps("Response code 200 is matched");
		String id = new APIUtilities().getValueFromResponse(resp, "data.id");
		String email = new APIUtilities().getValueFromResponse(resp, "data.email");
		String firstName = new APIUtilities().getValueFromResponse(resp, "data.first_name");
		String lastName = new APIUtilities().getValueFromResponse(resp, "data.last_name");
		String avatar = new APIUtilities().getValueFromResponse(resp, "data.avatar");
		String url = new APIUtilities().getValueFromResponse(resp, "support.url");
		String text = new APIUtilities().getValueFromResponse(resp, "support.text");

		// Validation after fetching data separately
		new Validations().verifyEquals(id, "2", "Id not matches");
		new AutomationReport().trackSteps("Id " + id + " is matched");
		new Validations().verifyEquals(email, "janet.weaver@reqres.in", "Email not matches");
		new AutomationReport().trackSteps("Email " + email + " is matched");
		new Validations().verifyEquals(firstName, "Janet", "First Name not matches");
		new AutomationReport().trackSteps("First Name " + firstName + " is matched");
		new Validations().verifyEquals(lastName, "Weaver", "Last Name not matches");
		new AutomationReport().trackSteps("Last Name " + lastName + " is matched");
		new Validations().verifyEquals(avatar, "https://reqres.in/img/faces/2-image.jpg", "Avatar not matches");
		new AutomationReport().trackSteps("Avatar " + avatar + " is matched");
		new Validations().verifyEquals(url, "https://reqres.in/#support-heading", "URL not matches");
		new AutomationReport().trackSteps("URL " + url + " is matched");
		new Validations().verifyEquals(text, "To keep ReqRes free, contributions towards server costs are appreciated!",
				"Text not matches");
		new AutomationReport().trackSteps("Text " + text + " is matched");

		// In-line validations
		boolean isDataIDExists = new APIValidations().validateJsonPath(resp, "data.id");
		new Validations().verifyEquals(isDataIDExists, true, "data.id is not exists");
		new APIValidations().validateJsonPathToIntegerValue(resp, "data.id", 2);
		new APIValidations().validateInResponseBody(resp, "janet.weaver@reqres.in");
		new APIValidations().validateResponseData(resp, "data.first_name", "Janet");

		new AutomationReport().trackSteps("User data retrieved successfully");

		// Negative testing
		Response response = new APIActions().getRequest(baseURI, "/api/users/23");
		new APIValidations().validateStatusCode(response, 404);
		new AutomationReport().trackSteps("Response code 404 is matched and user not found");
		boolean isEmpty = new APIValidations().validateEmptyResponse(response);
		new Validations().verifyEquals(isEmpty, true, "Response body is not empty");
	}

	@Test(enabled = true, description = "Register user")
	public void TC002_testPostAPIRequest() throws AutomationException {
		new AutomationReport().assignCategory("POST Request");

		Response resp = new APIActions().postRequest(baseURI, "/api/register", "register_payload", "application/json");

		new APIValidations().validateStatusCode(resp, 200);
		new AutomationReport().trackSteps("Response code 200 is matched");

		// Validation after registration
		String id = new APIUtilities().getValueFromResponse(resp, "id");
		String token = new APIUtilities().getValueFromResponse(resp, "token");
		new Validations().verifyEquals(id, "4", "Id not matches");
		new AutomationReport().trackSteps("Id " + id + " is matched");
		new Validations().verifyEquals(token, "QpwL5tke4Pnpja7X4", "Token not matches");
		new AutomationReport().trackSteps("Token " + token + " is matched");

		// In-line validations
		new APIValidations().validateJsonPathToIntegerValue(resp, "id", 4);
		new APIValidations().validateResponseData(resp, "token", "QpwL5tke4Pnpja7X4");

		new AutomationReport().trackSteps("User registered successfully");
	}

	@Test(enabled = true, description = "Update job")
	public void TC003_testPutAPIRequest() throws AutomationException {
		new AutomationReport().assignCategory("PUT Request");

		Response resp = new APIActions().putRequest(baseURI, "/api/users/2", "updateJob_payload", "application/json");

		new APIValidations().validateStatusCode(resp, 200);
		new AutomationReport().trackSteps("Response code 200 is matched");

		// Validation after job update
		String name = new APIUtilities().getValueFromResponse(resp, "name");
		String job = new APIUtilities().getValueFromResponse(resp, "job");
		new Validations().verifyEquals(name, "morpheus", "Name not matches");
		new AutomationReport().trackSteps("Name " + name + " is matched");
		new Validations().verifyEquals(job, "zion resident", "Job not not updated");
		new AutomationReport().trackSteps("Job " + job + " is not updated");

		// In-line validations
		new APIValidations().validateResponseData(resp, "name", "morpheus");
		new APIValidations().validateResponseData(resp, "job", "zion resident");

		new AutomationReport().trackSteps("Job details updated successfully");
	}

	@Test(enabled = true, description = "Update job using Patch request")
	public void TC004_testPatchAPIRequest() throws AutomationException {
		new AutomationReport().assignCategory("PATCH Request");

		Response resp = new APIActions().patchRequest(baseURI, "/api/users/2", "updateJob_payload", "application/json");

		new APIValidations().validateStatusCode(resp, 200);
		new AutomationReport().trackSteps("Response code 200 is matched");

		// Validation after job update
		String name = new APIUtilities().getValueFromResponse(resp, "name");
		String job = new APIUtilities().getValueFromResponse(resp, "job");
		new Validations().verifyEquals(name, "morpheus", "Name not matches");
		new AutomationReport().trackSteps("Name " + name + " is matched");
		new Validations().verifyEquals(job, "zion resident", "Job not not updated");
		new AutomationReport().trackSteps("Job " + job + " is not updated");

		// In-line validations
		new APIValidations().validateResponseData(resp, "name", "morpheus");
		new APIValidations().validateResponseData(resp, "job", "zion resident");

		new AutomationReport().trackSteps("Job details updated successfully");
	}

	@Test(enabled = true, description = "Delete user")
	public void TC005_testDeleteAPIRequest() throws AutomationException {
		new AutomationReport().assignCategory("DELETE Request");

		Response resp = new APIActions().deleteRequest(baseURI, "/api/users/2");

		new APIValidations().validateStatusCode(resp, 204);
		new AutomationReport().trackSteps("Response code 204 is matched and user deleted successfully");
	}

	@Test(enabled = true, description = "Delete user")
	public void TC006_testJSONDataUpdate() throws AutomationException, IOException {
		new AutomationReport().assignCategory("JSON Data Update");

		Response resp = new APIActions().getRequest(baseURI, "/api/users/2");

		new APIUtilities().updateJSONDataWithGivenFileName("simple_payload", null, "job", "Designer");
		new APIUtilities().updateJSONArrayDataWithGivenFileName("complex_payload", null, "topping", 1, "type", "Cool");
		ArrayList<String> data = new ArrayList<>();
		data.add("data");
		new APIUtilities().updateJSONDataInResponse(resp, data, "email", "sanojs.swaminathan@reqres.in");
		new AutomationReport().trackSteps("JSON Data updated successfully");
	}
}
