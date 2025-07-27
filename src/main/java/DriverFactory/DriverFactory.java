package DriverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    /* ThreadLocal to ensure each thread has its own instance of WebDriver.
       This is useful in parallel test execution scenarios.
       where multiple threads may be running tests simultaneously.
       It prevents interference between tests by ensuring that each thread has its own instance of the WebDriver.
       This is particularly important in frameworks like TestNG or JUnit where tests can be run in parallel.
       When a new thread is created, it will have its own instance of WebDriver, and when the thread completes, it can safely remove its instance. */
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static void setupDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                driverThreadLocal.set(new ChromeDriver(chromeOptions));
                break;
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--start-maximized");
                driverThreadLocal.set(new FirefoxDriver(firefoxOptions));
                break;
            default:
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--start-maximized");
                driverThreadLocal.set(new EdgeDriver(edgeOptions));

        }

    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void quitDriver() {
        getDriver().quit();
        driverThreadLocal.remove();
    }

}
