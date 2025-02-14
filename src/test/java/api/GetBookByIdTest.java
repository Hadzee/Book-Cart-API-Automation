package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetBookByIdTest {

    @Test
    public void getBookById() {

        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String baseURI = properties.getProperty("baseURI");
        String bookId = properties.getProperty("bookId");

        RestAssured.baseURI = baseURI;

        Response response = RestAssured.given()
                .basePath("/api/Book/{bookId}")
                .header("accept", "application/json")
                .pathParam("bookId", bookId)
                .when()
                .get();

        assertEquals(200, response.getStatusCode(), "Expected status code is 200.");
        assertNotNull(response.getBody().asString(), "Response body cannot be null.");
    }
}
