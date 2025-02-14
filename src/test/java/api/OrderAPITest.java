package api;

import helperClass.AuthenticationHelper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderAPITest {

    private static String token;

    @BeforeAll
    public static void tokenSetup() {
        token = AuthenticationHelper.authenticateAndGetToken();
    }

    @Test
    public void orderAPITest() throws IOException {

        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        RestAssured.baseURI = properties.getProperty("baseURI");
        String userId = properties.getProperty("userId");
        String bookId = properties.getProperty("bookId");

        Response postCartResponse = given()
                .header("Authorization", "Bearer " + token)
                .basePath("/api/ShoppingCart/AddToCart/{userId}/{bookId}")
                .pathParam("userId", userId)
                .pathParam("bookId", bookId)
                .header("Accept", "text/plain")
                .when()
                .post();

        assertEquals(200, postCartResponse.getStatusCode());
        assertNotNull(postCartResponse.asString());

        String checkoutRequestBody = new String(Files.readAllBytes(Paths.get("src/main/resources/checkoutRequestBody.json")));

        Response checkoutResponse = given()
                .header("Authorization", "Bearer " + token)
                .basePath("/api/CheckOut/{userId}")
                .pathParam("userId", userId)
                .header("accept", "*/*")
                .contentType(ContentType.JSON)
                .body(checkoutRequestBody)
                .when()
                .post();

        assertEquals(200, checkoutResponse.getStatusCode());
        assertNotNull(checkoutResponse.asString());

        Response getOrderResponse = given()
                .header("Authorization", "Bearer " + token)
                .basePath("/api/Order/{userId}")
                .pathParam("userId", userId)
                .header("accept", "application/json")
                .when()
                .get();

        assertEquals(200, getOrderResponse.getStatusCode());
        assertNotNull(getOrderResponse.asString());
    }
}
