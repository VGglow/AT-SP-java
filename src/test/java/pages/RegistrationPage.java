package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Alert;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationPage {

    SelenideElement PassengerName, PassportNumber, Email, Phone, FinishBtn, RegMessage;
    String successMessage, errorMessage;

    public RegistrationPage() {
        PassengerName = $("#passengerName");
        PassportNumber = $("#passportNumber");
        Email = $("#email");
        Phone = $("#phone");
        FinishBtn = $x("//button[.='Завершить регистрацию']");
        RegMessage = $("#registrationMessage");

        successMessage = "Бронирование завершено!";
        errorMessage = "Номер паспорта должен содержать только цифры и пробелы.";
    }

    public void makeRegistration() {
        FinishBtn.click();
    }

    public void setPassportNumber(String passport) {
//        PassengerName.setValue(fio);
        PassportNumber.setValue(passport);
//        Email.setValue(email);
//        Phone.setValue(phone);
    }

    public void verifyRegistration(String fio, String passport, String email, String phone) {
        Alert alert = switchTo().alert();
        assertTrue(alert.getText().contains(successMessage));
        assertTrue(alert.getText().contains("Пассажир: " + fio));
        assertTrue(alert.getText().contains("Паспорт: " + passport));
        assertTrue(alert.getText().contains("Email: " + email));
        assertTrue(alert.getText().contains("Телефон: " + phone));
//        sleep(3000);
        alert.accept();
    }

    public void verifyWrongPassport() {
        RegMessage.shouldHave(text(errorMessage));
    }
}
