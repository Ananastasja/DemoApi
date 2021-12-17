package tests;

import org.testng.annotations.Test;
import requers_objects.RegisteredUser;
import requers_objects.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ReqresPostTest {

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
                .log().all()
                .statusCode(201)
                .body("id", notNullValue())
                .body("createdAt", notNullValue());
    }

    @Test
    public void registrationSuccessfulTest() {
        RegisteredUser registeredUser = RegisteredUser.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();
        given()
                .body(registeredUser)
                .header("Content-Type", "application/json")
        .when()
                .post("https://reqres.in/api/register")
        .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(4))
                .body("token", notNullValue());
    }

    @Test
    public void registrationUnsuccessfulTest() {
        RegisteredUser registeredUser = RegisteredUser.builder()
                .email("eve.holt@reqres.in")
                .build();
        given()
                .body(registeredUser)
                .header("Content-Type", "application/json")
        .when()
                .post("https://reqres.in/api/register")
        .then()
                .log().all()
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }

    @Test
    public void loginSuccessfulTest() {
        RegisteredUser registeredUser = RegisteredUser.builder()
                .email("eve.holt@reqres.in")
                .password("cityslicka")
                .build();
        given()
                .body(registeredUser)
                .header("Content-Type", "application/json")
        .when()
                .post("https://reqres.in/api/login")
        .then()
                .log().all()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    public void loginUnsuccessfulTest() {
        RegisteredUser registeredUser = RegisteredUser.builder()
                .email("peter@klaven")
                .build();
        given()
                .body(registeredUser)
                .header("Content-Type", "application/json")
        .when()
                .post("https://reqres.in/api/login")
        .then()
                .log().all()
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }
}
