package Utils;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class GeneralUtils {

    private static final String SCREENSHOTS_PATH = "test-outputs/ScreenShots/";

    public static void clickingOnElement(WebDriver driver, By locator) {

        // Explicit wait to ensure the element is clickable before clicking
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(locator));

        driver.findElement(locator).click();
    }

    public static void sendData(WebDriver driver, By locator, String data) {

        // Explicit wait to ensure the element is visible before sending data
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));

        driver.findElement(locator).sendKeys(data);
    }

    public static String getText(WebDriver driver, By locator) {

        // Explicit wait to ensure the element is visible before getting text
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));

        return driver.findElement(locator).getText();

    }

    public static WebDriverWait generalWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public static WebElement findWebElement(WebDriver driver, By locator) {
        return driver.findElement(locator);
    }


    public static String getTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd-h-m-ssa").format(new Date());
    }

    public static void takingScreenShot(WebDriver driver, String screenshotName) {
        try {
            // capture screenshot using TakesScreenshot
            File screenshotSrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            //Save screenshot to a file if needed
            File screenshotFile = new File(SCREENSHOTS_PATH + screenshotName + "-" + getTimeStamp() + ".png");
            FileUtils.copyFile(screenshotSrc, screenshotFile);

            // Attach the screenshot to Allure
            Allure.addAttachment(screenshotName, Files.newInputStream(Path.of(screenshotFile.getPath())));

        } catch (Exception e) {
            LogsUtil.error(e.getMessage());
        }

    }

    public static void takingFullScreenshot(WebDriver driver, By locator) {
        try {
            Shutterbug.shootPage(driver, Capture.FULL_SCROLL)
                    .highlight(findWebElement(driver, locator))
                    .save(SCREENSHOTS_PATH);
        } catch (Exception e) {
            LogsUtil.error(e.getMessage());
        }

    }

    public static int generateRandomNumbers(int upperBound) {
        return new Random().nextInt(upperBound) + 1;
    }

    public static Set<Integer> generateUniqueNumbers(int numberOfProductsNeeded, int totalNumberOfProducts) {
        Set<Integer> generatedNumbers = new HashSet<>();
        while (generatedNumbers.size() < numberOfProductsNeeded) {
            int randomNumber = generateRandomNumbers(totalNumberOfProducts);
            generatedNumbers.add(randomNumber);
        }
        return generatedNumbers;
    }

    public static boolean verifyURL(WebDriver driver, String expectedURL) {
        try {
            generalWait(driver).until(ExpectedConditions.urlToBe(expectedURL));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    // ++++++++++++++++++++++++++++++++
    // to add last Log's file to Allure Report
    public static File getLatestFile(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        assert files != null;
        if (files.length == 0) {
            return null;
        }
        Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
        return files[0];
    }
    // ++++++++++++++++++++++++++++++++++++


    // ++++++++++++++++++++++++++++
    // to use Cookies
    public static Set<Cookie> getAllCookies(WebDriver driver) {
        return driver.manage().getCookies();
    }

    public static void restoreSession(WebDriver driver, Set<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            driver.manage().addCookie(cookie);
        }
    }
    // +++++++++++++++++++++++++++++++

}

