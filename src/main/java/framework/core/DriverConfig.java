package framework.core;


public class DriverConfig {
    static final String ANDROID_EMULATOR_NAME = "b1876fe1";
    static final String ANDROID_PLATFORM_NAME = "Android";
    static final String ANDROID_APP_PATH = "/artifacts/<.apk>";
    static final String ANDROID_PACKAGE_NAME = "<packagename>";
    static final String ANDROID_ACTIVITY_NAME = "<main-activityn-ame>";
    static final String ANDROID_VERSION = "8.0.0";
    public static final String DEVICE_NAME = "b1876fe1";
    static final String AUTOMATION_NAME = "UIAutomator2";
    static final boolean ANDROID_APP_FULL_RESET = false;

    static final String IOS_SIMULATOR_NAME = "iPhone 5";
    static final String IOS_PLATFORM_NAME = "iOS";
    static final String IOS_PLATFORM_VERSION = "10.3.3";
    static final String IOS_APP_PATH = "/artifacts/ios/<.ipa>";
    static final String IOS_SIMULATOR_UDID = "";
    static final String IOS_AUTOMATION_NAME = "XCUITest";
    static final boolean IOS_APP_NO_RESET = true;

    static final String CHROME_DRIVER_MAC = "/dependency/chromedriver_mac";
    static final String CHROME_DRIVER_WINDOWS = "/dependency/chromedriver_windows.exe";
    static final String CHROME_DRIVER_LINUX = "/dependency/chromedriver_linux";
    static final String PROD_URL = "https://www.ebay.de/";
    static final String LOCAL_URL = "http://localhost:8080/";
}