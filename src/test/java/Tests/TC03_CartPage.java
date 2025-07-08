package Tests;

import Listeners.IInvokedMethodListener;
import Listeners.ITestResultListener;
import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
import Pages.P03_CartPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtils.getPropertyValue;

@Listeners({IInvokedMethodListener.class, ITestResultListener.class})
public class TC03_CartPage {

    private final String USERNAME = DataUtils.getJsonData("validLogin", "username");
    private final String PASSWORD = DataUtils.getJsonData("validLogin", "password");

    @BeforeMethod
    public void setup() throws IOException {
        // condition ? true : false
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : getPropertyValue("environment", "BROWSER");
        LogsUtils.info(System.getProperty("browser"));
        setupDriver(browser);
        LogsUtils.info("Edge driver is opened");
        getDriver().get(getPropertyValue("environment", "BASE_URL"));
        LogsUtils.info("Page is redirected to the URL");
        getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));
    }


    @Test
    public void comparingPrices() {
        String totalPrice = new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLoginButton()
                .addRandomProducts(2, 6)
                .getTotalPriceOfSelectedProducts();

        new P02_LandingPage(getDriver()).clickOnCartIcon();

        Assert.assertTrue(new P03_CartPage(getDriver()).comparingPrices(totalPrice));
    }

    @AfterMethod
    public void quit() {
        quitDriver();

    }
}
