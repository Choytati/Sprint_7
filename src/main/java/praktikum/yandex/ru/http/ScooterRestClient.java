package praktikum.yandex.ru.http;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class ScooterRestClient {
    public final String SCOOTER_URL = "http://qa-scooter.praktikum-services.ru";
    protected RequestSpecification baseSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(SCOOTER_URL)
                .build();
    }
}
