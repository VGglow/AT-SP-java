import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.FlightsFoundList;
import pages.FlightsLoginPage;
import pages.FlightsSearchPage;
import pages.RegistrationPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;

@TestMethodOrder(MethodOrderer.DisplayName.class)
public class POMFlightsTests {
    @BeforeAll
    static void beforeAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        Configuration.browser = "edge";  // "chrome", "firefox", "edge"
        Configuration.browserSize = "1920x1200";
    }
    @BeforeEach
    void setUp() {
        open("https://slqa.ru/cases/DeepSeekFlights/");
//        getWebDriver().manage().window().maximize();
    }
    @AfterEach
    void pause5() {
        sleep(1500);
    }

    //Тест-кейсы

    @Test
    @DisplayName("POM-01. Неуспешный логин")
    void failedLoginTest() {
        FlightsLoginPage loginPage = new FlightsLoginPage();
        loginPage.login("standard_user", "wrong_stand_pass1");
        loginPage.verifyWrongUsernameOrPassword();
    }

    @Test
    @DisplayName("POM-02. Не задана дата вылета")
    void noDateTest() {
        FlightsLoginPage loginPage = new FlightsLoginPage();
        loginPage.login("visual_user", "visu_pass6");
        loginPage.verifySuccessfulLogin();

        FlightsSearchPage searchPage = new FlightsSearchPage();
        searchPage.findFlights("Москва","Париж", "");
        searchPage.verifyEmptyDate();
    }

    @Test
    @DisplayName("POM-03. Не найдены рейсы")
    void noFlightsFoundTest() {
        FlightsLoginPage loginPage = new FlightsLoginPage();
        loginPage.login("standard_user", "stand_pass1");
        loginPage.verifySuccessfulLogin();

        FlightsSearchPage searchPage = new FlightsSearchPage();
        searchPage.findFlights("Казань","Париж", "31.12.2025");
//        searchPage.verifyPastDate();

        //Добавить проверку, что не найдены рейсы
        FlightsFoundList flightsList = new FlightsFoundList();
        flightsList.verifyNotFoundFlights();

    }

    @Test
    @DisplayName("POM-04. Регистрация - некорректно заполнен номер паспорта")
    void wrongPassportTest() {
        FlightsLoginPage loginPage = new FlightsLoginPage();
        loginPage.login("visual_user", "visu_pass6");

        FlightsSearchPage searchPage = new FlightsSearchPage();
        searchPage.findFlights("Москва","Нью-Йорк", "31.12.2025");

        FlightsFoundList flightsList = new FlightsFoundList();
        flightsList.verifySuccessfullSearch();
        flightsList.chooseFirstFlight();

        //Добавить класс RegistrationPage и использовать его
        RegistrationPage regPage = new RegistrationPage();
//        regPage.makeRegistration("Федоров Алексей Николаевич", "6789 012345", "fedorov@example.com", "+7 (678) 901-2345");
        regPage.setPassportNumber("ZXDgfh7654");
        regPage.makeRegistration();
        regPage.verifyWrongPassport();
//        regPage.verifyRegistration("Федоров Алексей Николаевич", "6789 012345", "fedorov@example.com", "+7 (678) 901-2346");
    }

    @ParameterizedTest(name = "POM-05. Успешный логин. Проверка ФИО и паспорта.")
    @CsvSource({
            "standard_user,stand_pass1,Иванов Иван Иванович,1234 567890,ivanov@example.com,+7 (123) 456-7890",
            "performance_glitch_user,perf_pass4,Кузнецов Дмитрий Сергеевич,4567 890123,kuznetsov@example.com,+7 (456) 789-0123",
            "visual_user,visu_pass6,Федоров Алексей Николаевич,6789 012345,fedorov@example.com,+7 (678) 901-2345",
    })
    void successLoginTest(String username, String password, String fio, String passport, String email, String phone) {
        FlightsLoginPage loginPage = new FlightsLoginPage();
        loginPage.login(username, password);
        loginPage.verifySuccessfulLogin();
        loginPage.verifyFIO(fio);

        FlightsSearchPage searchPage = new FlightsSearchPage();
        searchPage.findFlights("Москва","Нью-Йорк", "31.12.2025");

        FlightsFoundList flightsList = new FlightsFoundList();
        flightsList.verifySuccessfullSearch();
        flightsList.chooseFirstFlight();

        RegistrationPage regPage = new RegistrationPage();
        regPage.makeRegistration();
        regPage.verifyRegistration(fio, passport, email, phone);
    }

}
