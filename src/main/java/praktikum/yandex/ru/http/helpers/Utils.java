package praktikum.yandex.ru.http.helpers;

import praktikum.yandex.ru.http.model.order.Color;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public String generateRandomDateInFuture() {
        long currentDate = new Date().getTime();
        long randomDateInFuture = ThreadLocalRandom.current().nextLong(currentDate + 86400000, currentDate + 604800000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(randomDateInFuture);
    }

    public String generateRandomDateInFuture(int dayRange) {
        long currentDate = new Date().getTime();
        long randomDateInFuture = ThreadLocalRandom.current().nextLong(currentDate + 86400000, currentDate + dayRange * 86400000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(randomDateInFuture);
    }

    public String generateRandomPhoneNumber() {
        int i1 = ThreadLocalRandom.current().nextInt(7, 8);
        int i2 = ThreadLocalRandom.current().nextInt(0, 1000);
        int i3 = ThreadLocalRandom.current().nextInt(0, 1000);
        int i4 = ThreadLocalRandom.current().nextInt(100);
        int i5 = ThreadLocalRandom.current().nextInt(100);
        return "+" + String.format("%d %03d% 03d% 2d% 2d", i1, i2, i3, i4, i5);
    }

    public Color generateRandomColor() {
        Color[] colors = Color.values();
        return colors[ThreadLocalRandom.current().nextInt(colors.length)];
    }
}
