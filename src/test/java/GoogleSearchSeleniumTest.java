import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GoogleSearchSeleniumTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.google.com");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void searchReturnsCorrectResult() {
        WebElement searchField = wait.until(ExpectedConditions.elementToBeClickable(By.name("q")));

        searchField.sendKeys("Selenide vs Selenium");
        searchField.submit();

        // 2 минуты ожидания, потому что постоянно капча вылезает
        WebElement results = new WebDriverWait(driver, Duration.ofMinutes(2))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("search")));

        String firstTitle = results.findElement(By.cssSelector("#search a h3")).getText();

        assertTrue(firstTitle.contains("Selenide"));
    }

    @Test
    void luckyButtonIsDisplayedAndEnabled(){
        By lucky = By.xpath("//input[@name='btnI' and (contains(@value,'повез') or contains(@value,'Feeling Lucky'))]");

        WebElement button = driver.findElements(lucky).stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .get();

        assertTrue(button.isDisplayed());
        assertTrue(button.isEnabled());
    }

    @Test
    void settingsPopupIsDisplayed() {
        WebElement settingsLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[text()='Настройки' or text()='Settings']")
        ));

        settingsLink.click();

        WebElement pop = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("g-menu[role='menu']")
        ));

        assertTrue(pop.isDisplayed());
    }

    @Test
    void logoAndFooterLinksShouldBeVisible() {
        WebElement logo = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("svg[aria-label='Google']")
        ));

        assertTrue(logo.isDisplayed());

        WebElement aboutLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[text()='Всё о Google' or text()='About']")
        ));

        assertTrue(aboutLink.isDisplayed());
        assertFalse(aboutLink.getText().isBlank());

        WebElement advertising = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[text()='Реклама' or text()='Advertising']")
        ));

        assertTrue(advertising.isDisplayed());
        assertTrue(advertising.getAttribute("href").contains("https://www.google.com"));
    }

}
