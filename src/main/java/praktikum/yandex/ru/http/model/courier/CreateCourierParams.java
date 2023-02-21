package praktikum.yandex.ru.http.model.courier;

import praktikum.yandex.ru.http.model.IParams;

public class CreateCourierParams implements IParams {
    private String login;
    private String password;
    private String firstName;

    public CreateCourierParams(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public CreateCourierParams() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
