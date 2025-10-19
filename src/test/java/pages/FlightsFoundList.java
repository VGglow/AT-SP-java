package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class FlightsFoundList {
    SelenideElement
        firstRegButton = $x("//button[@class='register-btn']"),
        flightsCount = $x("//div[@id='flightsCount']"),
        flightsContainer = $("#flightsContainer"),
        newSearchButton = $x("//button[@class='new-search-btn']");

//    ElementsCollection
//        flightsList = $$x("//table[@id='flightsContainer']/tbody/tr/td");

    public void chooseFirstFlight() {
        firstRegButton.click();
    }

    public void verifySuccessfullSearch() {
        //flightsCount.shouldNotHave(text("Найдено рейсов: 0"));
        //assertEquals(7, flightsList.size());
        flightsContainer.shouldNotHave(text("Рейсов по вашему запросу не найдено."));
        firstRegButton.shouldBe(visible);
    }

    public void verifyNotFoundFlights() {
        flightsContainer.shouldHave(text("Рейсов по вашему запросу не найдено."));
        firstRegButton.shouldNotBe(exist);
//        newSearchButton.shouldBe(visible);
    }

}
