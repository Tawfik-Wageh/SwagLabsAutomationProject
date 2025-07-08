package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P05_OverviewPage {

    private final By itemTotal = By.className("summary_subtotal_label");
    private final By tax = By.className("summary_tax_label");
    private final By total = By.className("summary_total_label");
    private final By finishButton = By.xpath("//a[text()='FINISH']");

    private final WebDriver driver;

    public P05_OverviewPage(WebDriver driver) {
        this.driver = driver;
    }

    public float getItemTotal() {
        return Float.parseFloat(Utility.getText(driver, itemTotal).replace("Item total: $", ""));
    }

    public float getTax() {
        return Float.parseFloat(Utility.getText(driver, tax).replace("Tax: $", ""));
    }

    public float getTotal() {
        LogsUtils.info("Actual Total Price : " + (Utility.getText(driver, total).replace("Total: $", "")));
        return Float.parseFloat(Utility.getText(driver, total).replace("Total: $", ""));
    }

    public String calculateTotalPrice() {
        LogsUtils.info("Calculated Total Price : " + (getItemTotal() + getTax()));
        return String.valueOf(getItemTotal() + getTax());
    }

    public boolean comparingPrices() {
        return calculateTotalPrice().equals(String.valueOf(getTotal()));
    }

    public P06_FinishingOrderPage clickOnFinishButton() {
        Utility.clickingOnElement(driver, finishButton);
        return new P06_FinishingOrderPage(driver);
    }
}
