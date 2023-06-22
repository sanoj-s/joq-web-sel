package com.snj.testcases;

import org.json.JSONException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.snj.exception.AutomationException;
import com.snj.keywords.APIActions;
import com.snj.reporting.AutomationReport;

import io.restassured.response.Response;

@Listeners(AutomationReport.class)
public class GraphQLSample {
	String baseURI, endPoint, graphQLQuery;

	@BeforeClass
	public void setup() {
		baseURI = "https://www.predic8.de";
		endPoint = "/fruit-shop-graphql?";
		graphQLQuery = "query{\r\n" + "  products(id: \"7\") {\r\n" + "    name\r\n" + "    price\r\n"
				+ "    category {\r\n" + "      name\r\n" + "    }\r\n" + "    vendor {\r\n" + "      name\r\n"
				+ "      id\r\n" + "    }\r\n" + "  }\r\n" + "}";
	}

	@Test
	public void testGraphql() throws JSONException, AutomationException {
		Response resp = new APIActions().postRequestWithGraphQLFile(baseURI, endPoint, "sample_query",
				"application/json");
		System.out.println(resp.asPrettyString());
	}
}
