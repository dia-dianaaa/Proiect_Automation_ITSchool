package HelperMethods;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AlertMethods {

    private final WebDriver driver;
    private WebDriverWait wait;

    public AlertMethods(WebDriver driver) {
        this.driver = driver;
    }

    public void alertOk() {
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    public void alertWithDelay(int seconds) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.alertIsPresent());
        alertOk();
    }

    public void alertConfirm() {
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    public void alertCancel() {
        Alert alert = driver.switchTo().alert();
        alert.dismiss();
    }

    public void alertText(String value) {
        Alert alert = driver.switchTo().alert();
        alert.sendKeys(value);
        alert.accept();
    }
}
