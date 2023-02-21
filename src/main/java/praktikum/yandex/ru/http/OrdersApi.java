package praktikum.yandex.ru.http;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import praktikum.yandex.ru.http.model.order.CreateOrderParams;

import static io.restassured.RestAssured.given;
public class OrdersApi extends ScooterRestClient {
    public ValidatableResponse createOrder(CreateOrderParams createOrderParams) {
        return given().spec(baseSpec())
                .when()
                .and()
                .body(createOrderParams)
                .post("/api/v1/orders")
                .then();
    }

    public Response getListOfOrders() {
        return given().spec(baseSpec())
                .when()
                .get("/api/v1/orders");
    }
}
