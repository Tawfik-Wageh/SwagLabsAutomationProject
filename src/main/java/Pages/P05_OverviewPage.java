package Pages;

import Utils.GeneralUtils;
import Utils.LogsUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P05_OverviewPage {

    // Locators :
    private final By itemTotal = By.className("summary_subtotal_label");
    private final By tax = By.className("summary_tax_label");
    private final By total = By.className("summary_total_label");
    private final By finishButton = By.xpath("//a[text()='FINISH']");

    // variables :
    private final WebDriver driver;

    // Constructor :
    public P05_OverviewPage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions ( Methods ) :
    public float getItemTotal() {
        return Float.parseFloat(GeneralUtils.getText(driver, itemTotal).replace("Item total: $", ""));
    }

    public float getTax() {
        return Float.parseFloat(GeneralUtils.getText(driver, tax).replace("Tax: $", ""));
    }

    public float getTotal() {
        LogsUtil.info("Actual Total Price : " + (GeneralUtils.getText(driver, total).replace("Total: $", "")));
        return Float.parseFloat(GeneralUtils.getText(driver, total).replace("Total: $", ""));
    }

    public String calculateTotalPrice() {
        LogsUtil.info("Calculated Total Price : " + (getItemTotal() + getTax()));
        return String.valueOf(getItemTotal() + getTax());
    }

    public boolean comparingPrices() {
        return calculateTotalPrice().equals(String.valueOf(getTotal()));
    }

    public P06_FinishingOrderPage clickOnFinishButton() {
        GeneralUtils.clickingOnElement(driver, finishButton);
        return new P06_FinishingOrderPage(driver);
    }
}
