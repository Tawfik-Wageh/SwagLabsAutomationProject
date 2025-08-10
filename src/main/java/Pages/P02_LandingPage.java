package Pages;

import Utils.GeneralUtils;
import Utils.LogsUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;

public class P02_LandingPage {

    // Variables :
    static float totalPrice = 0;
    private static List<WebElement> allProducts;
    private static List<WebElement> selectedProducts;
    private final WebDriver driver;

    // Locators :
    private final By addToCartButtonForAllProducts = By.xpath("//button[@class]");
    private final By numberOfProductsOnCartIcon = By.className("shopping_cart_badge");
    private final By numberOfSelectedProducts = By.xpath("//button[.='REMOVE']");
    private final By cartIcon = By.className("shopping_cart_link");
    private final By pricesOfSelectedProducts = By.xpath("//button[.=\"REMOVE\"] //preceding-sibling::div[@class='inventory_item_price']");
    private final By sortDropdown = By.className("product_sort_container");
    private final By productPrices = By.className("inventory_item_price");
    private final By menuButtonContainer = By.className("bm-burger-button");
    private final By logoutButton = By.id("logout_sidebar_link");
    private final By AboutButton = By.id("about_sidebar_link");

    // Constructor :
    public P02_LandingPage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions (Methods) :

    public P02_LandingPage addAllProductsToCart() {
        allProducts = driver.findElements(addToCartButtonForAllProducts);
        LogsUtil.info("number of all products : " + allProducts.size());
        for (int i = 1; i <= allProducts.size(); i++) {

            //dynamic locator :
            By addToCartButtonForAllProducts = By.xpath("(//button[@class])[" + i + "]");

            GeneralUtils.clickingOnElement(driver, addToCartButtonForAllProducts);
        }

        return this;
    }

    public String getNumberOfProductsOnCartIcon() {
        try {
            LogsUtil.info("number of products on cart :" + GeneralUtils.getText(driver, numberOfProductsOnCartIcon));
            return GeneralUtils.getText(driver, numberOfProductsOnCartIcon);
        } catch (Exception e) {
            LogsUtil.error(e.getMessage());
            return "0";
        }
    }

    public String getNumberOfSelectedProducts() {
        try {
            selectedProducts = driver.findElements(numberOfSelectedProducts);
            LogsUtil.info("selected products :" + (selectedProducts.size()));
            return String.valueOf(selectedProducts.size());
        } catch (Exception e) {
            LogsUtil.error(e.getMessage());
            return "0";
        }
    }

    public boolean comparingNumberOfSelectedProductsWithCart() {

        return getNumberOfProductsOnCartIcon().equals(getNumberOfSelectedProducts());
    }

    public P02_LandingPage addRandomProducts(int numberOfProductsNeeded, int totalNumberOfProducts) {
        Set<Integer> randomNumbers = GeneralUtils.generateUniqueNumbers(numberOfProductsNeeded, totalNumberOfProducts);
        for (int random : randomNumbers) {
            LogsUtil.info("random number : " + random);

            //dynamic locator :
            By addToCartButtonForAllProducts = By.xpath("(//button[@class])[" + random + "]");

            GeneralUtils.clickingOnElement(driver, addToCartButtonForAllProducts);

        }

        return this;
    }

    public void clickOnCartIcon() {
        GeneralUtils.clickingOnElement(driver, cartIcon);

    }

    public String getTotalPriceOfSelectedProducts() {
        try {
            List<WebElement> pricesOfSelectedProducts = driver.findElements(this.pricesOfSelectedProducts);
            for (int i = 1; i <= pricesOfSelectedProducts.size(); i++) {

                //dynamic locator :
                By elements = By.xpath("(//button[.=\"REMOVE\"] //preceding-sibling::div[@class='inventory_item_price'])[" + i + "]");

                String fullText = GeneralUtils.getText(driver, elements);
                LogsUtil.info("total price :" + totalPrice);
                totalPrice += Float.parseFloat(fullText.replace("$", ""));
            }
            return String.valueOf(totalPrice);
        } catch (Exception e) {
            LogsUtil.error(e.getMessage());
            return "0";
        }
    }

    public P02_LandingPage selectPriceLowToHigh() {
        GeneralUtils.selectDropdownByValue(driver, sortDropdown, "lohi");
        LogsUtil.info("Selected 'Price (low to high)' from dropdown");
        return this;
    }

    public List<String> getAllProductPrices() {
        List<WebElement> priceElements = driver.findElements(productPrices);
        return GeneralUtils.getTextFromElements(priceElements);
    }

    public boolean verifyPricesSortedLowToHigh() {
        List<String> prices = getAllProductPrices();
        boolean isSorted = GeneralUtils.isSortedAscending(prices);
        LogsUtil.info("Prices are sorted low to high: " + isSorted);
        return isSorted;
    }

    public void goBackToPreviousPage() {
        driver.navigate().back();
        LogsUtil.info("Navigated back to the previous page");
    }


    public P02_LandingPage clickOnMenuButton() {
        GeneralUtils.clickingOnElement(driver, menuButtonContainer);
        return this;
    }

    public P02_LandingPage clickOnAboutButton() {
        GeneralUtils.clickingOnElement(driver, AboutButton);
        return this;

    }

    public void clickOnLogoutButton() {
        GeneralUtils.clickingOnElement(driver, logoutButton);

    }

}
