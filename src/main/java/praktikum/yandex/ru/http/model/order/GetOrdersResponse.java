package praktikum.yandex.ru.http.model.order;

import java.util.List;

public class GetOrdersResponse {
    private List<Order> orders;
    private PageInfo pageInfo;
    private List<Station> availableStations;

    public List<Order> getOrders() {
        return orders;
    }
}