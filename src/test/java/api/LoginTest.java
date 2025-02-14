package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {

    @Test
    public void authenticateAndGetToken() {

        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        RestAssured.baseURI = properties.getProperty("baseURI");

        String userName = properties.getProperty("userName");
        String password = properties.getProperty("password");
        String token = properties.getProperty("token");

        Response loginResponse = RestAssured.given()
                .basePath("/api/Login")
                .header("accept", "*/*")
                .header("Content-Type", "application/json")
                .body(String.format("{\"username\": \"%s\", \"password\": \"%s\"}", userName, password))
                .when()
                .post();

        assertEquals(200, loginResponse.getStatusCode(), "Expected status code is 200.");
    }
}
