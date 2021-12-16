package tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import objects.VacanciesList;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLOutput;

import static io.restassured.RestAssured.given;

public class HeadhunterTest {

    @Test
    public void vacancyTest() {
        String body = given()
        .when()
                .get("https://api.hh.ru/vacancies?text=QA")
        .then()
                //.log().all()
                .statusCode(200)
                .extract().body().asString();

        VacanciesList vacanciesList = new Gson().fromJson(body, VacanciesList.class);
        int salaryTo = vacanciesList.getItems().get(0).getSalary().getTo();
        System.out.println("Salary 'to' is " + salaryTo);
        System.out.println("With expose: " + vacanciesList);

        VacanciesList vacanciesListWithoutExpose = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
                .fromJson(body, VacanciesList.class);
        System.out.println("Without expose: " + vacanciesListWithoutExpose);
    }
}
