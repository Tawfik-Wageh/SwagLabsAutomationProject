package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static Utils.GeneralUtils.findWebElement;

public class P06_FinishingOrderPage {

    // Variables:
    private final WebDriver driver;

    // Locators:
    private final By thanksMessage = By.tagName("h2");

    // Constructor:
    public P06_FinishingOrderPage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions (Methods ):
    public boolean checkVisibilityOfThanksMessage() {
        return findWebElement(driver, thanksMessage).isDisplayed();
    }
}
