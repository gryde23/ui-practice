import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;


public class GoogleSearchSelenideTest {

    @Test
    void searchTest() {
        open("https://www.google.com");
        $("[name='q']").setValue("Selenide vs Selenium").pressEnter();
        $("#search a h3").shouldHave(text("Selenide"), Duration.ofMinutes(2));
    }

    @Test
    void luckyButtonIsDisplayedAndEnabled() {
        open("https://www.google.com");
        SelenideElement lucky = $$(byAttribute("name", "btnI")).filter(visible).first();

        lucky.shouldBe(visible, enabled);
    }

    @Test
    void settingsCheck() {
        open("https://www.google.com");
        $(byText("Настройки")).shouldBe(visible);
    }

    @Test
    void logoAndFooterLinksCheck() {
        open("https://www.google.com");
        $("svg[aria-label='Google']").shouldBe(visible);

        $(byXpath("//a[text()='Всё о Google']")).shouldBe(visible);

        $(byText("Реклама")).shouldBe(visible)
                .shouldHave(attribute("href"));
    }
}
