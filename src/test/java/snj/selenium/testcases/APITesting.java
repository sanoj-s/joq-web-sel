package snj.selenium.testcases;

import org.testng.annotations.Test;

import com.snj.apihandler.APIActions;
import com.snj.apihandler.APIValidations;
import com.snj.exception.AutomationException;

import io.restassured.response.Response;

public class APITesting {
	@Test(enabled = true)
	public void TC001_sampleAPITestCase() throws AutomationException {
		Response responseData = new APIActions().getRequest("https://reqres.in/", "api/users/2");
		new APIValidations().validateStatusCode(responseData, 200);
	}
}
