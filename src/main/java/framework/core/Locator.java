package framework.core;

import framework.exceptionManager.LocatorNotSetException;
import framework.logger.LogUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.NoSuchElementException;

import static framework.core.Page.takeSnapShot;

public class Locator {

    public static WebDriver driver;
    private static String map;
    private static WebElement webElement;

    Locator() {
        new DriverManager();
        driver = DriverManager.getDriver();
    }

    WebElement getLocator(HashMap<String, String> locatorMap, int timeOut) throws Exception {
        map = getLocatorMap(locatorMap);
        webElement = getLocatorByType(map.split(":")[0].trim(), map.split(":")[1].trim(), timeOut);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
        return webElement;
    }

    public WebElement getLocator(HashMap<String, String> locatorMap) throws Exception {
        return getLocator(locatorMap, 10);
    }

    public Long getBrowserScrollPosition() {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        return (Long) executor.executeScript("return window.pageYOffset;");
    }

    private WebElement getLocatorByType(String key, String value, int timeOut) throws LocatorNotSetException, NoSuchElementException, StaleElementReferenceException {
        WebElement we;
        LogUtils.INFO("Looking for element By " + key + " - " + value);
        switch (key) {
            case "id":
                we = new WebDriverWait(driver, timeOut).until(ExpectedConditions.visibilityOfElementLocated(By.id(value)));
                break;
            case "xpath":
                we = new WebDriverWait(driver, timeOut).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(value)));
                break;
            case "class":
                we = new WebDriverWait(driver, timeOut).until(ExpectedConditions.visibilityOfElementLocated(By.className(value)));
                break;
            case "css":
                we = new WebDriverWait(driver, timeOut).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(value)));
                break;
            default:
                throw new LocatorNotSetException();
        }
        return we;

    }

    protected boolean isElementDisplayed(HashMap<String, String> locatorMap, int timeOut) {
        map = getLocatorMap(locatorMap);
        try {
            webElement = getLocatorByType(map.split(":")[0].trim(), map.split(":")[1].trim(), timeOut);
        } catch (Exception e) {
            return false;
        }
        return webElement.isDisplayed();
    }

    protected boolean isElementDisplayed(HashMap<String, String> locatorMap) {
        return isElementDisplayed(locatorMap, 10);
    }

    private String getLocatorMap(HashMap<String, String> locatorMap) {
        return DriverManager.driverType.equals("mweb") ? locatorMap.get("web") : locatorMap.get(DriverManager.driverType);
    }

    protected boolean waitElementNotDisplayed(HashMap<String, String> locatorMap) throws LocatorNotSetException, StaleElementReferenceException {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String key = getLocatorMap(locatorMap).split(":")[0].trim();
        String value = getLocatorMap(locatorMap).split(":")[1].trim();
        try {
            switch (key) {
                case "id":
                    wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id(value))));
                    break;
                case "xpath":
                    wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath(value))));
                    break;
                case "class":
                    wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.className(value))));
                    break;
                case "css":
                    wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(value))));
                    break;
                default:
                    throw new LocatorNotSetException();
            }
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }
}
