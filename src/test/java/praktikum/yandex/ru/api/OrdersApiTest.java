package praktikum.yandex.ru.api;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import praktikum.yandex.ru.http.OrdersApi;
import praktikum.yandex.ru.http.helpers.Utils;
import praktikum.yandex.ru.http.model.order.Color;
import praktikum.yandex.ru.http.model.order.CreateOrderParams;
import praktikum.yandex.ru.http.model.order.GetOrdersResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;

public class OrdersApiTest {
    private final OrdersApi api = new OrdersApi();
    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private ArrayList<Color> color;

    @Before
    public void setUp() {
        Utils utils = new Utils();
        this.firstName = RandomStringUtils.randomAlphabetic(10);
        this.lastName = RandomStringUtils.randomAlphabetic(4);
        this.address = RandomStringUtils.randomAlphabetic(10, 20);
        this.metroStation = ThreadLocalRandom.current().nextInt(1, 238);
        this.phone = utils.generateRandomPhoneNumber();
        this.rentTime = ThreadLocalRandom.current().nextInt(1, 8);
        this.deliveryDate = utils.generateRandomDateInFuture();
        this.comment = RandomStringUtils.randomAlphabetic(10);
    }

    @Test
    @DisplayName("POST/api/v1/orders. Order can be created with one color.")
    public void testOrderCanBeCreatedWithOneColor() {
        this.color = new ArrayList<>(Collections.singletonList(new Utils().generateRandomColor()));
        ValidatableResponse createOrderResponse = api.createOrder(new CreateOrderParams(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color));
        int expectedStatusCode = 201;
        int statusCode = createOrderResponse.extract().statusCode();
        assertEquals("The status code must be " + expectedStatusCode, expectedStatusCode, statusCode);
    }

    @Test
    @DisplayName("POST/api/v1/orders. Order can be created with two colors.")
    public void testOrderCanBeCreatedWithTwoColors() {
        this.color = new ArrayList<>(Arrays.asList(Color.BLACK, Color.GREY));
        ValidatableResponse createOrderResponse = api.createOrder(new CreateOrderParams(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color));
        int expectedStatusCode = 201;
        int statusCode = createOrderResponse.extract().statusCode();
        assertEquals("The status code must be " + expectedStatusCode, expectedStatusCode, statusCode);
    }

    @Test
    @DisplayName("POST/api/v1/orders. Order can be created without any color.")
    public void testOrderCanBeCreatedWithoutAnyColor() {
        ValidatableResponse createOrderResponse = api.createOrder(new CreateOrderParams(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, null));
        int expectedStatusCode = 201;
        int statusCode = createOrderResponse.extract().statusCode();
        assertEquals("The status code must be " + expectedStatusCode, expectedStatusCode, statusCode);
    }

    @Test
    @DisplayName("POST/api/v1/orders. Successful order creation returns track number")
    public void testSuccessfulOrderCreationReturnsTrackNumber() {
        this.color = new ArrayList<>(Collections.singletonList(new Utils().generateRandomColor()));
        ValidatableResponse createOrderResponse = api.createOrder(new CreateOrderParams(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color));
        int trackNumber = createOrderResponse.extract().path("track");
        MatcherAssert.assertThat(trackNumber, notNullValue());
    }

    @Test
    @DisplayName("GET/api/v1/orders. Response body contains the list of the orders.")
    public void testResponseBodyContainsTheListOfTheOrders() {
        Response response = api.getListOfOrders();
        GetOrdersResponse getOrdersResponse = response.body().as(GetOrdersResponse.class);
        MatcherAssert.assertThat(getOrdersResponse.getOrders(), notNullValue());
    }
}