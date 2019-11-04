package framework.core;

import framework.Utils.ConfigReader;
import framework.logger.LogUtils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DriverManager {

    protected static WebDriver driver;

    public static String driverType;
    private static String url;

    public static WebDriver getDriver() {
        return driver;
    }

    public static void startAUT() throws MalformedURLException {
        ConfigReader configReader = new ConfigReader();

        switch (configReader.desktopOs) {
            case ConfigReader.DESKTOP_OS_MAC:
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + DriverConfig.CHROME_DRIVER_MAC);
                break;
            case ConfigReader.DESKTOP_OS_WINDOWS:
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + DriverConfig.CHROME_DRIVER_WINDOWS);
                break;
            default:
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + DriverConfig.CHROME_DRIVER_LINUX);
                break;
        }

        switch (driverType) {
            case "android":
                DesiredCapabilities androidCapabilities = new DesiredCapabilities();
                androidCapabilities.setCapability("deviceName", DriverConfig.ANDROID_EMULATOR_NAME);
                androidCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, DriverConfig.ANDROID_VERSION);
                androidCapabilities.setCapability("platformName", DriverConfig.ANDROID_PLATFORM_NAME);
                androidCapabilities.setCapability("app", System.getProperty("user.dir") + DriverConfig.ANDROID_APP_PATH);
                androidCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, DriverConfig.ANDROID_PACKAGE_NAME);
                androidCapabilities.setCapability(MobileCapabilityType.FULL_RESET, DriverConfig.ANDROID_APP_FULL_RESET);
                androidCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, DriverConfig.ANDROID_ACTIVITY_NAME);
                androidCapabilities.setCapability(AndroidMobileCapabilityType.UNICODE_KEYBOARD, true);
                androidCapabilities.setCapability(AndroidMobileCapabilityType.RESET_KEYBOARD, DriverConfig.ANDROID_APP_FULL_RESET);
                androidCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, DriverConfig.AUTOMATION_NAME);
                driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), androidCapabilities);
                WebDriverWait androidWait = new WebDriverWait(driver, 30);
                break;
            case "ios":
                DesiredCapabilities iosCapabilities = new DesiredCapabilities();
                iosCapabilities.setCapability("deviceName", DriverConfig.IOS_SIMULATOR_NAME);
                iosCapabilities.setCapability("platformName", DriverConfig.IOS_PLATFORM_NAME);
                iosCapabilities.setCapability("platformVersion", DriverConfig.IOS_PLATFORM_VERSION);
                iosCapabilities.setCapability("automationName", DriverConfig.IOS_AUTOMATION_NAME);
                iosCapabilities.setCapability("udid", DriverConfig.IOS_SIMULATOR_UDID);
                iosCapabilities.setCapability(MobileCapabilityType.NO_RESET, DriverConfig.IOS_APP_NO_RESET);
                iosCapabilities.setCapability("app", System.getProperty("user.dir") + DriverConfig.IOS_APP_PATH);
                driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), iosCapabilities);
                WebDriverWait iOSWait = new WebDriverWait(driver, 30);
                break;
            case "web":
                String strBrowserType = System.getProperty("browser");
                if (strBrowserType.toLowerCase().equals("firefox")) {
                    driver = new FirefoxDriver();
                } else if (strBrowserType.toLowerCase().equals("chrome")) {
                    ChromeOptions options = new ChromeOptions();
//                    options.addArguments("headless");
                    options.addArguments("--no-sandbox");
                    options.addArguments("--force-device-scale-factor=2");
                    options.addArguments("--disable-dev-shm-usage");
                    options.addArguments("--window-size=1920,1080");
                    LogUtils.INFO("**** INFO: INSTANTIATING THE CHROME DRIVER ");
                    driver = new ChromeDriver(options);
                }
                driver.get(getURL());
                break;
            case "mweb":
                Map<String, String> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceName", "iPhone X");  //Device Type can be passed from command line
                ChromeOptions chromeOptions = new ChromeOptions();
//                chromeOptions.addArguments("headless");
                chromeOptions.addArguments("--window-size=1920,1080");
                chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                driver = new ChromeDriver(chromeOptions);
                driver.get(getURL());
                driver.manage().window().fullscreen();
                break;
            default:
                LogUtils.ERROR("Please pass the platform.");
                break;
        }
    }

    private static String getURL() {
        if (System.getProperty("env").toLowerCase().equals("prod"))
            url = DriverConfig.PROD_URL;
        else
            url = DriverConfig.LOCAL_URL;
        return url;
    }
}
