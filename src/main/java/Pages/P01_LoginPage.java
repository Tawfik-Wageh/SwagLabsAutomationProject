package Pages;

import Utils.GeneralUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class P01_LoginPage {

    // Locators for the login page elements :
    private final By username = By.id("user-name");
    private final By password = By.id("password");
    private final By loginButton = By.id("login-button");

    // variables :
    private final WebDriver driver;

    // Constructor:
    // The constructor initializes the WebDriver instance for the login page.
    public P01_LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions (Methods) :
    public P01_LoginPage enterUsername(String usernameText) {
        GeneralUtils.sendData(driver, username, usernameText);

        /* to stay fluent (fluent pattern Approach), return the current instance of P01_LoginPage.
           so that we can chain methods together.
           to stay on the current page.
           e.g., new P01_LoginPage(driver).enterUsername("user").enterPassword("pass").clickOnLoginButton(); */
        return this;
    }

    public P01_LoginPage enterPassword(String passwordText) {
        GeneralUtils.sendData(driver, password, passwordText);
        return this;
    }

    public P02_LandingPage clickOnLoginButton() {
        GeneralUtils.clickingOnElement(driver, loginButton);

        /* fluent pattern Approach:
           After clicking the login button, we expect to be redirected to the landing page.
           So we return a new instance of P02_LandingPage.
           This allows us to continue interacting with the landing page seamlessly.
           to take the driver with us to the next page so that we can call P02_LandingPage methods and add it to the chain.
           e.g., new P01_LoginPage(driver).enterUsername("user").enterPassword("pass").clickOnLoginButton().someLandingPageMethod(); */
        return new P02_LandingPage(driver);

    }

    // Assertion(Validations) for the login page :
    public boolean assertLoginTC(String expectedValue) {
        return driver.getCurrentUrl().equals(expectedValue);
    }

}
