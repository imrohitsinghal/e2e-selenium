package framework.core;

import framework.logger.LogUtils;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Page extends Locator {

    private static MonkeyPatch mpatch = new MonkeyPatch();
    static List<String> images = new ArrayList<>(); //For Storing images to create GIF in case of test failure
    HashMap<String, String> locatorMap = new HashMap<>();

    public void click(HashMap<String, String> locatorMap, int timeOut) throws Exception {
        takeSnapShot(driver);
        getLocator(locatorMap, timeOut).click();
        LogUtils.INFO("Page: CLICKED ON ELEMENT");
    }

    public void click(HashMap<String, String> locatorMap) throws Exception {
        takeSnapShot(driver);
        getLocator(locatorMap).click();
        LogUtils.INFO("Page: CLICKED ON ELEMENT");
    }

    protected void sendKeys(HashMap<String, String> locatorMap, String value) throws Exception {
        getLocator(locatorMap).sendKeys(value);
        LogUtils.INFO("Page: ENTERED VALUES: " + value);
    }

    public String getText(HashMap<String, String> locatorMap) throws Exception {
        String actualText = getLocator(locatorMap).getText();
        LogUtils.INFO("Page: GOT TEXT FROM THE ELEMENT: " + actualText);
        return actualText;
    }

    protected boolean isElementPresent(HashMap<String, String> locatorMap) throws Exception {
        boolean isDisplayed = isElementDisplayed(locatorMap);
        LogUtils.INFO(String.format("Page: IS ELEMENT PRESENT ON THE SCREEN: %s", isDisplayed));
        return isDisplayed;
    }

    protected void scrollToElement(HashMap<String, String> locatorMap) throws Exception {
        takeSnapShot(driver);
        new Actions().scrollTo(locatorMap);
    }

    protected void goToURL(String url) {
        LogUtils.INFO("Page: NAVIGATING TO URL: " + url);
        driver.get(url);
    }

    public void scrollDown(int times) throws InterruptedException {
        takeSnapShot(driver);
        new Actions().scrollDown(times);
    }

    protected String getURL() {
        return driver.getCurrentUrl();
    }

    protected void refreshPage() {
        driver.navigate().refresh();
    }

    protected void waitForPageLoad() {
        ExpectedCondition<Boolean> pageLoadCondition = driver -> {
            assert driver != null;
            return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
        };
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(pageLoadCondition);
    }

    public void scrollToTop() {
        takeSnapShot(driver);
        new Actions().scrollToTop();
    }

    static String takeSnapShot(WebDriver webdriver) {
        File DestFile = null;
        String fileWithPath = "./Screenshots/" + DriverManager.driverType + "/";
        //Convert web driver object to TakeScreenshot
        TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
        //Call getScreenshotAs method to create image file
        fileWithPath = System.getProperty("env").isEmpty() ? fileWithPath : fileWithPath + System.getProperty("env") + "/" + mpatch.getScenarioName().replace(' ', '_') + "/";
        String timestamp = new SimpleDateFormat("HHmmss").format(new Date());
        try {
            Thread.sleep(1000); // to make the page load before taking screenshot
            File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
            //Move image file to new destination
            DestFile = new File(fileWithPath + "screenshot_" + timestamp + ".jpg");
            //Copy file at destination
            images.add(String.valueOf(DestFile));
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (Exception e) {
            LogUtils.ERROR("Page: ERROR WHILE TAKING SCREENSHOT ");
            System.out.println(e.getMessage());
        }
        return String.valueOf(DestFile);
    }
}

