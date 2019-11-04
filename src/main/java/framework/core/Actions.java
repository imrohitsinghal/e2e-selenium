package framework.core;

import framework.logger.LogUtils;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class Actions extends Page {

    public void scrollTo(HashMap<String, String> locatorMap) throws Exception {
        ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].scrollIntoView(true);", getLocator(locatorMap));
    }

    public void scrollUp() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,0);");

    }

    public void scrollToTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,0);");

    }

    public void scrollUp(int scrollTimes) {
        for (int i = 0; i < scrollTimes; i++) {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,450);");
        }
    }

    public void scrollDown() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,250);");

    }

    public void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,document.body.scollHeight);");

    }

    public void scrollDown(int scrollTimes) throws InterruptedException {
        for (int i = 0; i < scrollTimes; i++) {
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,450)", "");
            Thread.sleep(2000);
        }
    }
}
