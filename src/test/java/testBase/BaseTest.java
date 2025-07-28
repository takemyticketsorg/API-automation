package testBase;

import org.testng.annotations.BeforeClass;
import io.restassured.RestAssured;

public class BaseTest {

	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://tmt-aks-prod.takemytickets.co.in";
	}

}
