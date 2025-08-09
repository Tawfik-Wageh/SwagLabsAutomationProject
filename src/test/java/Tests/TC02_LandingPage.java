package Tests;

import Listeners.IInvokedMethodListener;
import Listeners.ITestResultListener;
import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
import Utils.DataUtil;
import Utils.LogsUtil;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import static DriverFactory.DriverFactory.*;
import static Utils.DataUtil.getPropertyValue;
import static Utils.GeneralUtils.*;


@Listeners({IInvokedMethodListener.class, ITestResultListener.class})
public class TC02_LandingPage {

    private final String USERNAME = DataUtil.getJsonData("validLogin", "username");
    private final String PASSWORD = DataUtil.getJsonData("validLogin", "password");
    private Set<Cookie> cookies;

    @BeforeClass
    public void login() throws IOException {


        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : getPropertyValue("environment", "BROWSER");

        LogsUtil.info(System.getProperty("browser"));
        setupDriver(browser);
        LogsUtil.info("Edge driver is opened");
        getDriver().get(getPropertyValue("environment", "BASE_URL"));
        LogsUtil.info("Page is redirected to the URL");
        getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));

        new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLoginButton();

        // Save cookies after login to restore session later
        cookies = getAllCookies(getDriver());

        quitDriver();
    }


    @BeforeMethod
    public void setup() throws IOException {


        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : getPropertyValue("environment", "BROWSER");

        LogsUtil.info(System.getProperty("browser"));
        setupDriver(browser);
        LogsUtil.info("Edge driver is opened");
        getDriver().get(getPropertyValue("environment", "BASE_URL"));
        LogsUtil.info("Page is redirected to the URL");

        // Restore session by adding cookies
        restoreSession(getDriver(), cookies);

        getDriver().get(getPropertyValue("environment", "HOME_URL"));
        getDriver().navigate().refresh();

    }

    // Test Cases :
    @Test
    public void checkingNumberOfSelectedProducts() {
        new P02_LandingPage(getDriver()).addAllProductsToCart();
        Assert.assertTrue(new P02_LandingPage(getDriver()).comparingNumberOfSelectedProductsWithCart());
    }

    @Test
    public void addingRandomProductsToCart() {
        new P02_LandingPage(getDriver()).addRandomProducts(3, 6);
        Assert.assertTrue(new P02_LandingPage(getDriver()).comparingNumberOfSelectedProductsWithCart());
    }

    @Test
    public void clickOnCartIcon() throws IOException {
        new P02_LandingPage(getDriver()).clickOnCartIcon();
        Assert.assertTrue(verifyURL(getDriver(), DataUtil.getPropertyValue("environment", "CART_URL")));
    }

    @AfterMethod
    public void quit() {
        quitDriver();
    }

    @AfterClass
    public void deleteSession() {

        // clear cookies after finishing session
        cookies.clear();
    }

}
