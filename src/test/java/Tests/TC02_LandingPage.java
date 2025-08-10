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

    @BeforeClass(alwaysRun = true)
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


    @BeforeMethod(alwaysRun = true)
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
    @Test(priority = 1)
    public void checkingNumberOfSelectedProducts() {
        new P02_LandingPage(getDriver()).addAllProductsToCart();
        Assert.assertTrue(new P02_LandingPage(getDriver()).comparingNumberOfSelectedProductsWithCart());
    }

    @Test(priority = 2)
    public void addingRandomProductsToCart() {
        new P02_LandingPage(getDriver()).addRandomProducts(3, 6);
        Assert.assertTrue(new P02_LandingPage(getDriver()).comparingNumberOfSelectedProductsWithCart());
    }

    @Test(priority = 3)
    public void verifyPriceSortingLowToHigh() {
        P02_LandingPage landingPage = new P02_LandingPage(getDriver());

        landingPage.selectPriceLowToHigh();
        boolean isSorted = landingPage.verifyPricesSortedLowToHigh();

        Assert.assertTrue(isSorted, "Prices are not sorted correctly (low to high)");
    }

    @Test(priority = 4)
    public void clickOnCartIcon() throws IOException {
        new P02_LandingPage(getDriver()).clickOnCartIcon();
        Assert.assertTrue(verifyURL(getDriver(), DataUtil.getPropertyValue("environment", "CART_URL")));
    }

    @Test(priority = 5)
    public void clickOnAboutButton() throws IOException {
        new P02_LandingPage(getDriver()).clickOnMenuButton().clickOnAboutButton();
        Assert.assertTrue(verifyURL(getDriver(), DataUtil.getPropertyValue("environment", "ABOUT_URL")));
    }

    @Test(priority = 6)
    public void testGoBackToPreviousPage() throws IOException {
        new P02_LandingPage(getDriver()).clickOnMenuButton().clickOnAboutButton().goBackToPreviousPage();
        Assert.assertTrue(verifyURL(getDriver(), DataUtil.getPropertyValue("environment", "HOME_URL")));
    }

    @Test(priority = 7)
    public void clickOnLogoutButton() throws IOException {
        new P02_LandingPage(getDriver()).clickOnMenuButton().clickOnLogoutButton();
        Assert.assertTrue(verifyURL(getDriver(), DataUtil.getPropertyValue("environment", "BASE_URL")));

    }


    @AfterMethod(alwaysRun = true)
    public void quit() {
        quitDriver();
    }

    @AfterClass(alwaysRun = true)
    public void deleteSession() {

        // clear cookies after finishing session
        cookies.clear();
    }

}
