package tests;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class ReqresDeleteTest {

    @Test
    public void deleteUserTest() {
        given()
        .when()
                .delete("https://reqres.in/api/users/2")
        .then()
                .log().all()
                .statusCode(204);
    }
}
