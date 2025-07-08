package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;

public class P02_LandingPage {
    static float totalPrice = 0;
    private static List<WebElement> allProducts;
    private static List<WebElement> selectedProducts;
    private final WebDriver driver;
    private final By addToCartButtonForAllProducts = By.xpath("//button[@class]");
    private final By numberOfProductsOnCartIcon = By.className("shopping_cart_badge");
    private final By numberOfSelectedProducts = By.xpath("//button[.='REMOVE']");
    private final By cartIcon = By.className("shopping_cart_link");
    private final By pricesOfSelectedProducts = By.xpath("//button[.=\"REMOVE\"] //preceding-sibling::div[@class='inventory_item_price']");


    public P02_LandingPage(WebDriver driver) {
        this.driver = driver;
    }

    // Getter Method for "numberOfProductsOnCartIcon" to use it in "afterInvocation" Method in "IInvokedMethodListener" Class (Encapsulation)
    public By getNumberOfSelectedProductsOnCart() {
        return numberOfProductsOnCartIcon;
    }

    public P02_LandingPage addAllProductsToCart() {
        allProducts = driver.findElements(addToCartButtonForAllProducts);
        LogsUtils.info("number of all products : " + allProducts.size());
        for (int i = 1; i <= allProducts.size(); i++) {

            //dynamic locator
            By addToCartButtonForAllProducts = By.xpath("(//button[@class])[" + i + "]");

            Utility.clickingOnElement(driver, addToCartButtonForAllProducts);
        }

        return this;
    }

    public String getNumberOfProductsOnCartIcon() {
        try {
            LogsUtils.info("number of products on cart :" + Utility.getText(driver, numberOfProductsOnCartIcon));
            return Utility.getText(driver, numberOfProductsOnCartIcon);
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return "0";
        }
    }

    public String getNumberOfSelectedProducts() {
        try {
            selectedProducts = driver.findElements(numberOfSelectedProducts);
            LogsUtils.info("selected products :" + (selectedProducts.size()));
            return String.valueOf(selectedProducts.size());
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return "0";
        }
    }

    public boolean comparingNumberOfSelectedProductsWithCart() {

        return getNumberOfProductsOnCartIcon().equals(getNumberOfSelectedProducts());
    }

    public P02_LandingPage addRandomProducts(int numberOfProductsNeeded, int totalNumberOfProducts) {
        Set<Integer> randomNumbers = Utility.generateUniqueNumbers(numberOfProductsNeeded, totalNumberOfProducts);
        for (int random : randomNumbers) {
            LogsUtils.info("random number : " + random);
            //dynamic locator
            By addToCartButtonForAllProducts = By.xpath("(//button[@class])[" + random + "]");
            Utility.clickingOnElement(driver, addToCartButtonForAllProducts);

        }
        // to stay in the current page
        return this;
    }

    public P03_CartPage clickOnCartIcon() {
        Utility.clickingOnElement(driver, cartIcon);
        return new P03_CartPage(driver);
    }

    public String getTotalPriceOfSelectedProducts() {
        try {
            List<WebElement> pricesOfSelectedProducts = driver.findElements(this.pricesOfSelectedProducts);
            for (int i = 1; i <= pricesOfSelectedProducts.size(); i++) {

                //dynamic locator
                By elements = By.xpath("(//button[.=\"REMOVE\"] //preceding-sibling::div[@class='inventory_item_price'])[" + i + "]");

                String fullText = Utility.getText(driver, elements);
                LogsUtils.info("total price :" + totalPrice);
                totalPrice += Float.parseFloat(fullText.replace("$", ""));
            }
            return String.valueOf(totalPrice);
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return "0";
        }
    }

}
