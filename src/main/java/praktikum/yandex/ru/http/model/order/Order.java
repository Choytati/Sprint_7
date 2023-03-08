package praktikum.yandex.ru.http.model.order;

import java.util.List;

public class Order {
    private int id;
    private String courierId;
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private int track;
    private List<Color> color;
    private String comment;
    private String createdAt;
    private String updatedAt;
    private int status;

    public Order() {
    }

    public void printCourierId() {
        System.out.println(lastName);
    }
}
