package api;

import helperClass.AuthenticationHelper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WishlistAPITest {

    private static String token;

    @BeforeAll
    public static void tokenSetup() {
        token = AuthenticationHelper.authenticateAndGetToken();
    }

    @Test
    public void testToggleWishlist() {

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

        Response response = given()
                .basePath("/api/Wishlist/ToggleWishlist/{userId}/{bookId}")
                .pathParam("userId", userId)
                .pathParam("bookId", bookId)
                .header("Authorization", "Bearer " + token)
                .header("accept", "text/plain")
                .contentType(ContentType.TEXT)
                .when()
                .post();

        assertEquals(200, response.getStatusCode());
        assertNotNull(response.asString());

        Response getResponse = given()
                .basePath("/api/Wishlist/{userId}")
                .pathParam("userId", userId)
                .header("accept", "text/plain")
                .contentType(ContentType.TEXT)
                .when()
                .get();

        assertEquals(200, getResponse.getStatusCode());
        assertNotNull(getResponse.asString());

        Response deleteResponse = given()
                .basePath("/api/Wishlist/{userId}")
                .pathParam("userId", userId)
                .header("Authorization", "Bearer " + token)
                .header("accept", "text/plain")
                .contentType(ContentType.TEXT)
                .when()
                .delete();

        assertEquals(200, deleteResponse.getStatusCode());
        assertNotNull(deleteResponse.asString());
    }
}
