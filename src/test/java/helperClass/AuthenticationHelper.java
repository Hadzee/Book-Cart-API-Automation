package helperClass;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AuthenticationHelper {

    private static String token;

    private static Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(input);
        }
        return properties;
    }

    public static String authenticateAndGetToken() {
        if (token != null) {
            return token;
        }

        try {
            Properties properties = loadProperties();
            RestAssured.baseURI = properties.getProperty("baseURI");

            String userName = properties.getProperty("userName");
            String password = properties.getProperty("password");

            Response loginResponse = RestAssured.given()
                    .basePath("/api/Login")
                    .header("accept", "*/*")
                    .header("Content-Type", "application/json")
                    .body(String.format("{\"username\": \"%s\", \"password\": \"%s\"}", userName, password))
                    .when()
                    .post();

            if (loginResponse.getStatusCode() == 200) {
                token = loginResponse.jsonPath().getString("token");
                return token;
            } else {
                throw new RuntimeException("Failed to authenticate, status code: " + loginResponse.getStatusCode());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties or authenticate", e);
        }
    }
}
