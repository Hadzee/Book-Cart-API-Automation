package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import helperClass.AuthenticationHelper;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ShoppingCartTest {

    private static String token;

    @BeforeAll
    public static void tokenSetup() {
        token = AuthenticationHelper.authenticateAndGetToken();
    }

    @Test
    public void shoppingCartAPI() {

        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        RestAssured.baseURI = properties.getProperty("baseURI");
        String userId = properties.getProperty("userId");
        String newUserId = properties.getProperty("newUserId");
        String bookId = properties.getProperty("bookId");

        Response postCartResponse = given()
                .header("Authorization", "Bearer " + token)
                .basePath("/api/ShoppingCart/AddToCart/{userId}/{bookId}")
                .pathParam("userId", userId)
                .pathParam("bookId", bookId)
                .header("accept", "text/plain")
                .contentType(ContentType.TEXT)
                .when()
                .post();

        assertEquals(200, postCartResponse.getStatusCode());
        assertNotNull(postCartResponse.asString());

        Response putCartResponse = given()
                .header("Authorization", "Bearer " + token)
                .basePath("/api/ShoppingCart/{userId}/{bookId}")
                .pathParam("userId", userId)
                .pathParam("bookId", bookId)
                .header("accept", "text/plain")
                .contentType(ContentType.TEXT)
                .when()
                .put();

        assertEquals(200, putCartResponse.getStatusCode());
        assertNotNull(putCartResponse.asString());

        Response removeBookResponse = given()
                .header("Authorization", "Bearer " + token)
                .basePath("/api/ShoppingCart/{userId}/{bookId}")
                .pathParam("userId", userId)
                .pathParam("bookId", bookId)
                .header("accept", "text/plain")
                .contentType(ContentType.TEXT)
                .when()
                .delete();

        assertEquals(200, removeBookResponse.getStatusCode());
        assertNotNull(removeBookResponse.asString());

        Response getResponse = given()
                .header("Authorization", "Bearer " + token)
                .basePath("/api/ShoppingCart/SetShoppingCart/{userId}/{newUserId}")
                .pathParam("userId", userId)
                .pathParam("newUserId", newUserId)
                .header("accept", "text/plain")
                .contentType(ContentType.TEXT)
                .when()
                .get();

        assertEquals(200, getResponse.getStatusCode());
        assertNotNull(getResponse.asString());

        Response getCartResponse = given()
                .basePath("/api/ShoppingCart/{userId}")
                .pathParam("userId", userId)
                .header("accept", "text/plain")
                .contentType(ContentType.TEXT)
                .when()
                .get();

        assertEquals(200, getCartResponse.getStatusCode());
        assertNotNull(getCartResponse.asString());

        Response deleteCartResponse = given()
                .header("Authorization", "Bearer " + token)
                .basePath("/api/ShoppingCart/{userId}")
                .pathParam("userId", userId)
                .header("accept", "text/plain")
                .contentType(ContentType.TEXT)
                .when()
                .delete();

        assertEquals(200, deleteCartResponse.getStatusCode());
        assertNotNull(deleteCartResponse.asString());
    }
}
