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

    // Constructor :
    public P02_LandingPage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions (Methods) :
    public By getNumberOfSelectedProductsOnCart() {
        return numberOfProductsOnCartIcon;
    }

    public P02_LandingPage addAllProductsToCart() {
        allProducts = driver.findElements(addToCartButtonForAllProducts);
        LogsUtil.info("number of all products : " + allProducts.size());
        for (int i = 1; i <= allProducts.size(); i++) {

            //dynamic locator
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

            //dynamic locator
            By addToCartButtonForAllProducts = By.xpath("(//button[@class])[" + random + "]");

            GeneralUtils.clickingOnElement(driver, addToCartButtonForAllProducts);

        }

        return this;
    }

    public P03_CartPage clickOnCartIcon() {
        GeneralUtils.clickingOnElement(driver, cartIcon);
        return new P03_CartPage(driver);
    }

    public String getTotalPriceOfSelectedProducts() {
        try {
            List<WebElement> pricesOfSelectedProducts = driver.findElements(this.pricesOfSelectedProducts);
            for (int i = 1; i <= pricesOfSelectedProducts.size(); i++) {

                //dynamic locator
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

}
