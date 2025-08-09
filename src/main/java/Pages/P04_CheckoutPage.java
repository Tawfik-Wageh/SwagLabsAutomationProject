package Pages;

import Utils.GeneralUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P04_CheckoutPage {

    // Variables:
    private final WebDriver driver;

    // Locators:
    private final By firstName = By.id("first-name");

    private final By lastName = By.id("last-name");

    private final By zipCode = By.id("postal-code");

    private final By continueButton = By.xpath("//input[@value='CONTINUE']");

    // Constructor:
    public P04_CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    //Actions (Methods):
    public P04_CheckoutPage fillingInformationForm(String fName, String lName, String zip) {
        GeneralUtils.sendData(driver, firstName, fName);
        GeneralUtils.sendData(driver, lastName, lName);
        GeneralUtils.sendData(driver, zipCode, zip);
        return this;

    }

    public P05_OverviewPage clickingOnContinueButton() {
        GeneralUtils.clickingOnElement(driver, continueButton);
        return new P05_OverviewPage(driver);
    }


}
