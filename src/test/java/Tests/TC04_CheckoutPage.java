package Tests;

import Listeners.IInvokedMethodListener;
import Listeners.ITestResultListener;
import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
import Pages.P03_CartPage;
import Pages.P04_CheckoutPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import Utilities.Utility;
import com.github.javafaker.Faker;
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
public class TC04_CheckoutPage {

    private final String USERNAME = DataUtils.getJsonData("validLogin", "username");

    private final String PASSWORD = DataUtils.getJsonData("validLogin", "password");

    private final String firstName = DataUtils.getJsonData("information", "FIRSTNAME") + "-" + Utility.getTimeStamp();

    private final String lastName = DataUtils.getJsonData("information", "LASTNAME") + "-" + Utility.getTimeStamp();

    private final String zipCode = new Faker().number().digits(5);

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
    public void checkoutStepOne() throws IOException {

        //TODO:Login Steps
        new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLoginButton();
        //TODO:Adding products steps
        new P02_LandingPage(getDriver())
                .addRandomProducts(2, 6)
                .clickOnCartIcon();
        //TODO:Go to checkout Page steps
        new P03_CartPage(getDriver())
                .clickOnCheckoutButton();
        //TODO:filling information steps
        new P04_CheckoutPage(getDriver()).fillingInformationForm(firstName, lastName, zipCode)
                .clickingOnContinueButton();

        LogsUtils.info(firstName + " " + lastName + " " + zipCode);
        Assert.assertTrue(Utility.verifyURL(getDriver(), getPropertyValue("environment", "CHECKOUT_URL")));
    }

    @AfterMethod
    public void quit() {
        quitDriver();

    }
}
