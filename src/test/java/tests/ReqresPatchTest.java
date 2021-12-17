package tests;

import org.testng.annotations.Test;
import requers_objects.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ReqresPatchTest {

    @Test
    public void updateUserTest() {
        User user = User.builder()
                .name("morpheus")
                .job("zion resident")
                .build();
        given()
                .body(user)
                .header("Content-Type", "application/json")
        .when()
                .patch("https://reqres.in/api/users/2")
        .then()
                .log().all()
                .statusCode(200)
                .body("job", equalTo("zion resident"))
                .body("updatedAt", notNullValue());
    }
}
