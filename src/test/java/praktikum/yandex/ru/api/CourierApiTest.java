package praktikum.yandex.ru.api;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.yandex.ru.http.CourierApi;
import praktikum.yandex.ru.http.model.courier.CreateCourierParams;
import praktikum.yandex.ru.http.model.courier.LoginCourierParams;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;

public class CourierApiTest {
    private final CourierApi api = new CourierApi();
    private String login;
    private String password;
    private String firstName;
    private CreateCourierParams createCourierParams;
    private LoginCourierParams loginCourierParams;

    @Before
    public void setUp() {
        this.login = RandomStringUtils.randomAlphabetic(10);
        this.password = RandomStringUtils.randomAlphabetic(4);
        this.firstName = RandomStringUtils.randomAlphabetic(10);
        createCourierParams = new CreateCourierParams(login, password, firstName);
    }

    @After
    public void tearDown() {
        loginCourierParams = new LoginCourierParams(login, password);
        if (api.getCourierId(loginCourierParams) != null) {
            api.deleteCourierById(api.getCourierId(loginCourierParams));
        }
    }

    @Test
    @DisplayName("/praktikum/yandex/ru/api/v1/courier. Status code and response body when user is being created.")
    public void testCourierCanBeCreated() {
        ValidatableResponse response = api.createCourier(createCourierParams);
        int expectedStatusCode = 201;
        int statusCode = response.extract().statusCode();
        assertEquals("The status code must be " + expectedStatusCode, expectedStatusCode, statusCode);
        LinkedHashMap<String, Boolean> expectedBody = new LinkedHashMap<>();
        expectedBody.put("ok", true);
        Map<String, Boolean> body = response.extract().path("$");
        assertEquals("The response body must be " + expectedBody, expectedBody, body);
    }

    @Test
    @DisplayName("/praktikum/yandex/ru/api/v1/courier. Status code and response body when attempting to create a user with an existed login.")
    public void testCourierCannotBeCreatedWithAlreadyExistedLogin() {
        api.createCourier(createCourierParams);
        ValidatableResponse response = api.createCourier(new CreateCourierParams(login, "1234", "example"));
        int expectedStatusCode = 409;
        int statusCode = response.extract().statusCode();
        assertEquals("The status code must be " + expectedStatusCode, expectedStatusCode, statusCode);
        String expectedBody = "Этот логин уже используется. Попробуйте другой.";
        String body = response.extract().path("message");
        assertEquals("The response body must be \"" + expectedBody + "\"", expectedBody, body);
    }

    @Test
    @DisplayName("/praktikum/yandex/ru/api/v1/courier. Status code and response body when attempting to create a user without a login.")
    public void testCourierCannotBeCreatedWithoutLogin() {
        ValidatableResponse response = api.createCourier(new CreateCourierParams(null, password, firstName));
        int expectedStatusCode = 400;
        int statusCode = response.extract().statusCode();
        assertEquals("The status code must be " + expectedStatusCode, expectedStatusCode, statusCode);
        String expectedBody = "Недостаточно данных для создания учетной записи";
        String body = response.extract().path("message");
        assertEquals("The response body must be \"" + expectedBody + "\"", expectedBody, body);
    }

    @Test
    @DisplayName("/praktikum/yandex/ru/api/v1/courier. Status code and response body when attempting to create a user without a password.")
    public void testCourierCannotBeCreatedWithoutPassword() {
        ValidatableResponse response = api.createCourier(new CreateCourierParams(login, null, firstName));
        int expectedStatusCode = 400;
        int statusCode = response.extract().statusCode();
        assertEquals("The status code must be " + expectedStatusCode, expectedStatusCode, statusCode);
        String expectedBody = "Недостаточно данных для создания учетной записи";
        String body = response.extract().path("message");
        assertEquals("The response body must be \"" + expectedBody + "\"", expectedBody, body);
    }

    @Test
    @DisplayName("/praktikum/yandex/ru/api/v1/courier/login. Status code when courier logs in")
    public void testStatusCodeWhenCourierLogsIng() {
        api.createCourier(createCourierParams);
        ValidatableResponse loginResponse = api.loginByCourier(new LoginCourierParams(login, password));
        int expectedStatusCode = 200;
        int statusCode = loginResponse.extract().statusCode();
        assertEquals("The status code must be " + expectedStatusCode, expectedStatusCode, statusCode);
    }

    @Test
    @DisplayName("/praktikum/yandex/ru/api/v1/courier/login. Response body contains id when successfully logged in")
    public void testResponseBodyContainsIdWhenSuccess() {
        api.createCourier(createCourierParams);
        ValidatableResponse loginResponse = api.loginByCourier(new LoginCourierParams(login, password));
        int courierId = loginResponse.extract().path("id");
        MatcherAssert.assertThat(courierId, notNullValue());
    }

    @Test
    @DisplayName("/praktikum/yandex/ru/api/v1/courier/login. Status code and response body when login is not sent")
    public void testCourierCannotLogInWithoutLogin() {
        api.createCourier(createCourierParams);
        ValidatableResponse loginResponse = api.loginByCourier(new LoginCourierParams(null, password));
        int expectedStatusCode = 400;
        int statusCode = loginResponse.extract().statusCode();
        assertEquals("The status code must be " + expectedStatusCode, expectedStatusCode, statusCode);
        String expectedBody = "Недостаточно данных для входа";
        String loginResponseBody = loginResponse.extract().path("message");
        assertEquals("The response body must be \"" + expectedBody + "\"", expectedBody, loginResponseBody);
    }

    @Test
    @DisplayName("/praktikum/yandex/ru/api/v1/courier/login. Status code and response body when password is not sent")
    public void testCourierCannotLogInWithoutPassword() {
        api.createCourier(createCourierParams);
        ValidatableResponse loginResponse = api.loginByCourier(new LoginCourierParams(login, null));
        int expectedStatusCode = 400;
        int statusCode = loginResponse.extract().statusCode();
        assertEquals("The status code must be " + expectedStatusCode, expectedStatusCode, statusCode);
        String expectedBody = "Недостаточно данных для входа";
        String loginResponseBody = loginResponse.extract().path("message");
        assertEquals("The response body must be \"" + expectedBody + "\"", expectedBody, loginResponseBody);
    }

    @Test
    @DisplayName("/praktikum/yandex/ru/api/v1/courier/login. Status code and response body when login is incorrect")
    public void testCourierCannotLogInWithIncorrectLogin() {
        api.createCourier(createCourierParams);
        ValidatableResponse loginResponse = api.loginByCourier(new LoginCourierParams(login + "yo", password));
        int expectedStatusCode = 404;
        int statusCode = loginResponse.extract().statusCode();
        assertEquals("The status code must be " + expectedStatusCode, expectedStatusCode, statusCode);
        String expectedBody = "Учетная запись не найдена";
        String loginResponseBody = loginResponse.extract().path("message");
        assertEquals("The response body must be \"" + expectedBody + "\"", expectedBody, loginResponseBody);
    }

    @Test
    @DisplayName("/praktikum/yandex/ru/api/v1/courier/login. Status code and response body when password is incorrect")
    public void testCourierCannotLogInWithIncorrectPassword() {
        api.createCourier(createCourierParams);
        ValidatableResponse loginResponse = api.loginByCourier(new LoginCourierParams(login, password + "1"));
        int expectedStatusCode = 404;
        int statusCode = loginResponse.extract().statusCode();
        assertEquals("The status code must be " + expectedStatusCode, expectedStatusCode, statusCode);
        String expectedBody = "Учетная запись не найдена";
        String loginResponseBody = loginResponse.extract().path("message");
        assertEquals("The response body must be \"" + expectedBody + "\"", expectedBody, loginResponseBody);
    }
}