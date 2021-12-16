package tests;

import org.testng.annotations.Test;
import requers_objects.User;

import static io.restassured.RestAssured.given;

public class ReqresTest {

    @Test
    public void postCreateUserTest() {
        //https://reqres.in/api/users
        User user = User.builder()
                .name("morpheus")
                .job("leader")
                .build();
        given()
                .body(user)
        .when()
                .post("https://reqres.in/api/users")
        .then()
                .statusCode(201);
    }
}
