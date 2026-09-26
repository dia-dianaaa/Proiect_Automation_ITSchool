package SharedData.Browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeServiceBrowser implements IBrowserService {
    private WebDriver driver;

    @Override
    public void openBrowser() {
        ChromeOptions options = (ChromeOptions) browserOptions(); //ce e in paranteza este un cast
        driver = new ChromeDriver(options);
    }

    @Override
    public Object browserOptions() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("start-maximized");
        options.addArguments("no-sandbox");
        return options;
    }

    public WebDriver getDriver() {
        return driver;
    }
}
