package tests;

import com.google.gson.Gson;
import org.testng.Assert;
import org.testng.annotations.Test;
import requers_objects.ResourcesList;
import requers_objects.UserDataList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ReqresGetTest {

    @Test
    public void getUserInfoTest() {
        String body = given()
        .when()
                .get("https://reqres.in/api/users?page=2")
        .then()
                .log().all()
                .statusCode(200)
                .body("total", equalTo(12))
                .extract().body().asString();

        UserDataList userDataList = new Gson().fromJson(body, UserDataList.class);
        int idFromApi = userDataList.getData().get(0).getId();
        String emailFromApi = userDataList.getData().get(0).getEmail();
        String firstNameFromApi = userDataList.getData().get(0).getFirstName();
        String lastNameFromApi = userDataList.getData().get(0).getLastName();
        String avatarFromApi = userDataList.getData().get(0).getAvatar();
        Assert.assertEquals(idFromApi, 7);
        Assert.assertEquals(emailFromApi, "michael.lawson@reqres.in");
        Assert.assertEquals(firstNameFromApi, "Michael");
        Assert.assertEquals(lastNameFromApi, "Lawson");
        Assert.assertEquals(avatarFromApi, "https://reqres.in/img/faces/7-image.jpg");
    }

    @Test
    public void getSingleUserTest() {
        given()
        .when()
                .get("https://reqres.in/api/users/2")
        .then()
                .log().all()
                .statusCode(200)
                .body("data.last_name", equalTo("Weaver"))
                .body("support.text", equalTo("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }

    @Test
    public void getSingleUserNotFoundTest() {
        given()
        .when()
                .get("https://reqres.in/api/users/23")
        .then()
                .log().all()
                .statusCode(404)
                .body(equalTo("{}"));
    }

    @Test
    public void getResourcesInfoTest() {
        String body = given()
        .when()
                .get("https://reqres.in/api/unknown")
        .then()
                .log().all()
                .statusCode(200)
                .extract().body().asString();

        ResourcesList resourcesList = new Gson().fromJson(body, ResourcesList.class);
        int idFromApi = resourcesList.getData().get(0).getId();
        String nameFromApi = resourcesList.getData().get(0).getName();
        int yearFromApi = resourcesList.getData().get(0).getYear();
        String colorFromApi = resourcesList.getData().get(0).getColor();
        String valueFromApi = resourcesList.getData().get(0).getValue();
        Assert.assertEquals(idFromApi, 1);
        Assert.assertEquals(nameFromApi, "cerulean");
        Assert.assertEquals(yearFromApi, 2000);
        Assert.assertEquals(colorFromApi, "#98B2D1");
        Assert.assertEquals(valueFromApi, "15-4020");
    }

    @Test
    public void getSingleResourceTest() {
        given()
        .when()
                .get("https://reqres.in/api/unknown/2")
        .then()
                .log().all()
                .statusCode(200)
                .body("data.color", equalTo("#C74375"))
                .body("support.url", equalTo("https://reqres.in/#support-heading"));
        //TODO: how to compare the whole body?
    }

    @Test
    public void getSingleResourceNotFoundTest() {
        given()
        .when()
                .get("https://reqres.in/api/unknown/23")
        .then()
                .log().all()
                .statusCode(404)
                .body(equalTo("{}"));
    }

    @Test
    public void delayedResponseTest() {
        String body = given()
        .when()
                .get("https://reqres.in/api/users?delay=3")
        .then()
                .log().all()
                .statusCode(200)
                .body("total", equalTo(12))
                .body("data.id", hasItems(1, 2, 3))
                .and().time(lessThan(5000L))
                .extract().body().asString();

        UserDataList userDataList = new Gson().fromJson(body, UserDataList.class);
        String emailFromApi = userDataList.getData().get(0).getEmail();
        String avatarFromApi = userDataList.getData().get(0).getAvatar();
        Assert.assertEquals(emailFromApi, "george.bluth@reqres.in");
        Assert.assertEquals(avatarFromApi, "https://reqres.in/img/faces/1-image.jpg");
    }
}
