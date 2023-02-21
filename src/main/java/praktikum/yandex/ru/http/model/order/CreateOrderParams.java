package praktikum.yandex.ru.http.model.order;

import praktikum.yandex.ru.http.model.IParams;

import java.util.List;

public class CreateOrderParams implements IParams {
    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<Color> color;

    public CreateOrderParams(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, List<Color> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }
}
