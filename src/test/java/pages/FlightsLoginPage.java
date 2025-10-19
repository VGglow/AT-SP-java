package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class FlightsLoginPage {

    SelenideElement Username, Password, LoginButton, Message, Greeting;
    String successMessage, errorMessage;

    public FlightsLoginPage() {
        Username = $("#username");
        Password = $("#password");
        LoginButton = $("#loginButton");
        Message = $("#message");
        Greeting = $("#greeting");

        successMessage = "Вход в систему выполнен успешно! Загрузка...";
        errorMessage = "Неверное имя пользователя или пароль.";
    }

    public void login(String username, String password) {
        Username.setValue(username);
        Password.setValue(password);
        LoginButton.click();
    }

    public void verifySuccessfulLogin() {
        Message.shouldHave(text(successMessage));
    }

    public void verifyWrongUsernameOrPassword() {
        Message.shouldHave(text(errorMessage));
    }

    public void verifyFIO(String fio) {
        Greeting.shouldHave(text("Добро пожаловать, " + fio + "!"));
    }

}
