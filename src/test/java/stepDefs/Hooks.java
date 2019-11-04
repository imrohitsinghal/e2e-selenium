package stepDefs;

import com.aventstack.extentreports.ExtentReports;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import framework.core.AppiumServer;
import framework.core.DriverManager;
import framework.core.MonkeyPatch;
import framework.logger.LogUtils;
import org.im4java.core.IM4JavaException;

import java.io.IOException;

public class Hooks {

    public Scenario scenario;

    @Before
    public void beforeClass() {
        LogUtils.INFO("Initializing the driver instance");
    }

    @Before
    public void getScenarioName(Scenario scenario) {
        this.scenario = scenario;
    }

    @Before()
    public void beforeTestsRun() throws Exception {
        DriverManager.driverType = System.getProperty("platform"); // Getting the platform name from gradle commandline and setting this value in build.gradle
        MonkeyPatch.setScenarioName(scenario.getName());
        cleanup(scenario.getName());
        LogUtils.INFO("Scenario: " + scenario.getName());
        switch (DriverManager.driverType) {
            case "web":
            case "mweb":
                DriverManager.startAUT();
                break;
            case "android":
            case "ios":
                AppiumServer.start();
                DriverManager.startAUT();
                break;
            default:
                LogUtils.ERROR("Please specify the platform type");
                break;
        }
    }

    private void cleanup(String scenario) throws IOException {
        MonkeyPatch.cleanScreenshotAndGif(scenario);
    }

    @After()
    public void afterTestsRun() throws InterruptedException, IOException, IM4JavaException {
        addScreenshotsAndGifToReport();
        try {
            LogUtils.INFO("**** Shutting Webdriver ****");
            DriverManager.getDriver().quit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DriverManager.getDriver().quit();
            MonkeyPatch.clearImages();
        }
    }

    private void addScreenshotsAndGifToReport() throws InterruptedException, IOException, IM4JavaException {

        if (scenario.isFailed())  //for failing scenarios
        {
            MonkeyPatch.takeScreenshot();
            byte[] html = MonkeyPatch.genGif();
            scenario.embed(html, "image/gif");
            byte[] sourcePath = MonkeyPatch.screenshotInByte(); // to have screenshot for failure screen
            scenario.embed(sourcePath, "image/png");
        }
    }

    @After
    public void isFailing(Scenario scenario) {
        this.scenario = scenario;
    }
}