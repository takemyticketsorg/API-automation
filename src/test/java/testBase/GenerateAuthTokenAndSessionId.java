package testBase;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GenerateAuthTokenAndSessionId {

	public static String generateAuthToken() {
		// Sending GET request 
		Response response = RestAssured
				.given()
				.log().uri()
				.header("Content-Type", "application/json")
				.when()
				.get("/api/v1/auth/guest-token")
				.then()
				.extract()
				.response();

		//print response
		response.prettyPrint();

		return response.jsonPath().getString("data.guestToken");
	}

	public static String getSessionIdFromEvents(String token) {
		// Sending GET request 
		Response response = RestAssured.given()
				.log().uri()
				.header("Content-Type", "application/json")
				.queryParam("location", "Chennai")
				.queryParam("isTrending", "true")
				.queryParam("limit", "5")
				.header("X-Auth-Token",token)
				.when()
				.get("/api/v1/events/search");
		
		//print response
		response.prettyPrint();
		return response.jsonPath().getString("data.primaryEvents[0]._id");
	}

	public static Response callGetByIdAPI(String sessionId,String token) {
		return  RestAssured.given()
				.log().uri()
				.header("Content-Type", "application/json")
				.queryParam("buyerFlow", "true")
				.header("X-Auth-Token",token)
				.when()
				.get("/api/v1/events/"+sessionId);
	}

}
