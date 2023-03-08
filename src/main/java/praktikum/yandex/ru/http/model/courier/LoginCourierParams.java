package praktikum.yandex.ru.http.model.courier;

public class LoginCourierParams {
    private String login;
    private String password;

    public LoginCourierParams(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public LoginCourierParams() {

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
}
