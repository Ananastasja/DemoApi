package tests;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class OnlinerTest {

    @Test
    public void getCurrencyRateTest() {
        //https://www.onliner.by/sdapi/kurs/api/bestrate?currency=USD&type=nbrb
        given()
                .log().all()
        .when()
                .get("https://www.onliner.by/sdapi/kurs/api/bestrate?currency=RUB&type=nbrb")
        .then()
                .log().all()
                .statusCode(200)
                .body("amount", equalTo("3,4251"));
    }
}
