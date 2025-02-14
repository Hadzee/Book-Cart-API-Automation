package api;

import helperClass.AuthenticationHelper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class APIUserTest {

    private static String token;

    @BeforeAll
    public static void tokenSetup() {
        token = AuthenticationHelper.authenticateAndGetToken();
    }

    @Test
    public void testUserAPI() {

        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String baseURI = properties.getProperty("baseURI");
        String userId = properties.getProperty("userId");
        String userName = properties.getProperty("userName");

        RestAssured.baseURI = baseURI;

        Response userResponse = given()
                .basePath("/api/User/{userId}")
                .pathParam("userId", userId)
                .header("accept", "text/plain")
                .contentType(ContentType.TEXT)
                .when()
                .get();

        assertEquals(200, userResponse.getStatusCode(), "Expected status code 200");
        assertNotNull(userResponse.asString(), "Response body should not be null");

        Response getResponse = given()
                .basePath("/api/User/validateUserName/{userName}")
                .pathParam("userName", userName)
                .header("accept", "text/plain")
                .contentType(ContentType.TEXT)
                .when()
                .get();

        assertEquals(200, getResponse.getStatusCode(), "Expected status code 200");
        assertNotNull(getResponse.asString(), "Response body should not be null");

        File payloadFile = new File("src/main/resources/userAPI.json");

        Response userPostResponse = given()
                .header("Authorization", "Bearer " + token)
                .header("accept", "*/*")
                .contentType(ContentType.JSON)
                .body(payloadFile)
                .when()
                .post("/api/User");

        assertEquals(200, userPostResponse.getStatusCode(), "Expected status code 200");
        assertNotNull(userPostResponse.asString(), "Response body should not be null");
    }
}
