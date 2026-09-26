package SharedData.Browser;

import Logger.LoggerUtility;
import SharedData.ConfigReader;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

public class ShareDataDriver {
    private WebDriver driver;
    public String testName;

    @BeforeMethod
    public void initializeBrowser() {
        ThreadContext.put("testName", this.getClass().getSimpleName());
        testName = this.getClass().getSimpleName();
        driver = new BrowserFactory().getBrowserFactory();
        LoggerUtility.infoTestCase("===== The browser started successfully");
        LoggerUtility.startTestCase(testName);
        driver.get(ConfigReader.getBaseUrl());
    }

    @AfterMethod
    public void clearBrowser(ITestResult result) {
        if (driver != null) {
            driver.quit();
        }
        LoggerUtility.infoTestCase("The browser was closed successfully");
        if (result.getStatus() == ITestResult.FAILURE && result.getThrowable() != null) {
            LoggerUtility.errorLog(result.getThrowable().getMessage());
        }
        LoggerUtility.infoTestCase("===== The browser closed successfully");
        LoggerUtility.endTestCase(testName);
    }

    @AfterSuite
    public void finishLogFiles() {
        LoggerUtility.mergeFiles();
    }

    public WebDriver getDriver() {
        return driver;
    }
}
