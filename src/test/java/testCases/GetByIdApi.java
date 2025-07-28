package testCases;

import org.testng.annotations.Test;
import io.restassured.response.Response;
import testBase.BaseTest;
import testBase.GenerateAuthTokenAndSessionId;

public class GetByIdApi extends BaseTest {

	private String token;
    private String sessionId;
    
@Test(priority=1)
public void testGenerateAuthToken() {
	token =GenerateAuthTokenAndSessionId.generateAuthToken();
	System.out.println("Auth Token: " + token);
}

@Test(priority = 2, dependsOnMethods = "testGenerateAuthToken")
public void testGetSessionId() {
    sessionId = GenerateAuthTokenAndSessionId.getSessionIdFromEvents(token);
    System.out.println("Session ID: " + sessionId);
}

@Test(priority = 3, dependsOnMethods = "testGetSessionId")
public void testFinalAPIUsingSessionId() {
    Response response = GenerateAuthTokenAndSessionId.callGetByIdAPI(sessionId, token);
    response.prettyPrint();
    response.then().statusCode(200);
    System.out.println("Final API Response: " + response.asString());
}

}
