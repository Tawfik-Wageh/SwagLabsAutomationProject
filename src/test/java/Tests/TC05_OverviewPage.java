package Tests;

import Listeners.IInvokedMethodListener;
import Listeners.ITestResultListener;
import Pages.*;
import Utils.DataUtil;
import Utils.GeneralUtils;
import Utils.LogsUtil;
import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;
import static Utils.DataUtil.getPropertyValue;
import static Utils.GeneralUtils.verifyURL;

@Listeners({IInvokedMethodListener.class, ITestResultListener.class})
public class TC05_OverviewPage {
    private final String USERNAME = DataUtil.getJsonData("validLogin", "username");

    private final String PASSWORD = DataUtil.getJsonData("validLogin", "password");

    private final String firstName = DataUtil.getJsonData("information", "FIRSTNAME") + "-" + GeneralUtils.getTimeStamp();

    private final String lastName = DataUtil.getJsonData("information", "LASTNAME") + "-" + GeneralUtils.getTimeStamp();

    private final String zipCode = new Faker().number().digits(5);

    @BeforeMethod
    public void setup() throws IOException {


        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : getPropertyValue("environment", "BROWSER");

        LogsUtil.info(System.getProperty("browser"));
        setupDriver(browser);
        LogsUtil.info("Edge driver is opened");
        getDriver().get(getPropertyValue("environment", "BASE_URL"));
        LogsUtil.info("Page is redirected to the URL");
        getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));
    }

    // Test Cases :
    @Test
    public void checkoutStepTwo() throws IOException {

        //TODO:Login Steps
        new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLoginButton();
        //TODO:Adding products steps
        new P02_LandingPage(getDriver())
                .addAllProductsToCart()
                .clickOnCartIcon();
        //TODO:Go to checkout Page steps
        new P03_CartPage(getDriver())
                .clickOnCheckoutButton();
        //TODO:filling information steps
        new P04_CheckoutPage(getDriver()).fillingInformationForm(firstName, lastName, zipCode)
                .clickingOnContinueButton();
        LogsUtil.info(firstName + " " + lastName + " " + zipCode);

        Assert.assertTrue(new P05_OverviewPage(getDriver()).comparingPrices());
    }

    @Test
    public void cancelCheckout() throws IOException {
        //TODO:Login Steps
        new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLoginButton();
        //TODO:Adding products steps
        new P02_LandingPage(getDriver())
                .addAllProductsToCart()
                .clickOnCartIcon();
        //TODO:Go to checkout Page steps
        new P03_CartPage(getDriver())
                .clickOnCheckoutButton();
        //TODO:filling information steps
        new P04_CheckoutPage(getDriver()).fillingInformationForm(firstName, lastName, zipCode)
                .clickingOnContinueButton();
        //TODO:Cancel checkout steps
        new P05_OverviewPage(getDriver())
                .clickOnCancelButton();

        LogsUtil.info("Cancel button is clicked");

        Assert.assertTrue(verifyURL(getDriver(), DataUtil.getPropertyValue("environment", "HOME_URL")));


    }

    @AfterMethod
    public void quit() {
        quitDriver();

    }

}
