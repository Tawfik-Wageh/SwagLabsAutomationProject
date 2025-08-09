package Pages;

import Utils.GeneralUtils;
import Utils.LogsUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class P03_CartPage {

    // Variables :
    static float totalPrice = 0;
    private final WebDriver driver;

    // Locators :
    private final By pricesOfSelectedProducts = By.xpath("//button[.=\"REMOVE\"] //preceding-sibling::div[@class='inventory_item_price']");
    private final By checkoutButton = By.xpath("//a[@class='btn_action checkout_button']");

    // Constructor :
    public P03_CartPage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions (Methods) :
    public String getTotalPrice() {
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

    public boolean comparingPrices(String price) {
        return getTotalPrice().equals(price);
    }

    public P04_CheckoutPage clickOnCheckoutButton() {
        GeneralUtils.clickingOnElement(driver, checkoutButton);
        return new P04_CheckoutPage(driver);

    }
}
