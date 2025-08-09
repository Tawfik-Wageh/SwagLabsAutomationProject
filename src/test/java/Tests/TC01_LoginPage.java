package Tests;

import Listeners.IInvokedMethodListener;
import Listeners.ITestResultListener;
import Pages.P01_LoginPage;
import Utils.DataUtil;
import Utils.LogsUtil;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;
import static Utils.DataUtil.getPropertyValue;

@Listeners({IInvokedMethodListener.class, ITestResultListener.class})
public class TC01_LoginPage {

    private final String USERNAME = DataUtil.getJsonData("validLogin", "username");
    private final String PASSWORD = DataUtil.getJsonData("validLogin", "password");

    @BeforeMethod
    public void setup() throws IOException {

        // Shorthand if statement (condition ? true: false)
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : getPropertyValue("environment", "BROWSER");

        LogsUtil.info(System.getProperty("browser"));
        setupDriver(browser);
        LogsUtil.info("driver is opened");
        getDriver().get(getPropertyValue("environment", "BASE_URL"));
        LogsUtil.info("Page is redirected to the URL");
        getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));
    }

    // Test Cases :
    @Test
    public void validLoginTC() throws IOException {

        // fluent pattern Approach using Anonymous Object :
        new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLoginButton();

        // Assertion(Validation) for the login page :
        Assert.assertTrue(new P01_LoginPage(getDriver()).assertLoginTC(getPropertyValue("environment", "HOME_URL")));
    }

    @AfterMethod
    public void quit() {
        quitDriver();

    }

}
