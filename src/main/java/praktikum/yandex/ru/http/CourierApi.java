package praktikum.yandex.ru.http;

import io.restassured.response.ValidatableResponse;
import praktikum.yandex.ru.http.model.courier.CreateCourierParams;
import praktikum.yandex.ru.http.model.courier.LoginCourierParams;

import static io.restassured.RestAssured.given;

public class CourierApi extends ScooterRestClient {

    public ValidatableResponse createCourier(CreateCourierParams createCourierPostData) {
        return given().spec(baseSpec())
                .when()
                .and()
                .body(createCourierPostData)
                .post("/api/v1/courier")
                .then();
    }

    public ValidatableResponse loginByCourier(LoginCourierParams loginCourierPostData) {
        return given().spec(baseSpec())
                .when()
                .and()
                .body(loginCourierPostData)
                .post("/api/v1/courier/login")
                .then();
    }

    public Integer getCourierId(LoginCourierParams loginCourierPostData) {
        return given().spec(baseSpec())
                .when()
                .and()
                .body(loginCourierPostData)
                .post("/api/v1/courier/login")
                .then()
                .extract()
                .path("id");
    }

    public void deleteCourierById(Integer id) {
        given().spec(baseSpec())
                .when()
                .and()
                .delete("/api/v1/courier/" + id)
                .then();
    }
}
