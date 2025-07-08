package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class P03_CartPage {

    static float totalPrice = 0;
    private final By pricesOfSelectedProducts = By.xpath("//button[.=\"REMOVE\"] //preceding-sibling::div[@class='inventory_item_price']");
    private final By checkoutButton = By.xpath("//a[@class='btn_action checkout_button']");

    private final WebDriver driver;

    public P03_CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTotalPrice() {
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

    public boolean comparingPrices(String price) {
        return getTotalPrice().equals(price);
    }

    public P04_CheckoutPage clickOnCheckoutButton() {
        Utility.clickingOnElement(driver, checkoutButton);
        return new P04_CheckoutPage(driver);

    }
}
