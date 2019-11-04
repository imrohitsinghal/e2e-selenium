package framework.core;

import framework.logger.LogUtils;
import org.apache.commons.io.FileUtils;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.*;
import java.util.StringTokenizer;

import static framework.core.DriverManager.*;

public class MonkeyPatch extends Page {

    private static String scenarioName;
    private static String scenarioStatus;

    public static void setStatus(String status) {
        scenarioStatus = status;
    }

    private static String getScreenshotDir(String scenarioName) {
        String fileWithPath = "./Screenshots/" + driverType + "/";
        return System.getProperty("env").isEmpty() ? fileWithPath : fileWithPath + System.getProperty("env") + "/" + scenarioName.replace(' ', '_') + "/";
    }

    private static String getGifDir() {
        String fileWithPath = "./Gif/" + driverType + "/";
        return System.getProperty("env").isEmpty() ? fileWithPath : fileWithPath + System.getProperty("env") + "/";
    }

    public static byte[] genGif() throws InterruptedException, IOException, IM4JavaException, FileNotFoundException {
        ConvertCmd cmd = new ConvertCmd();
        IMOperation op = new IMOperation();
        op.delay(100);
        op.loop(0);
        String filepath = getGifDir() + scenarioName.replace(" ", "_") + ".gif";
        for (String image : images) {
            op.addImage(image);
        }
        op.addImage(filepath);
        cmd.run(op);
        LogUtils.INFO("GIF Generated for the scenario - " + scenarioName + " , at - " + filepath);
        return convertToByte(filepath);
    }

    private static byte[] convertToByte(String filepath) throws IOException {

        File file = new File(filepath);
        byte[] bytesArray = new byte[(int) file.length()];

        FileInputStream fis = new FileInputStream(file);
        fis.read(bytesArray);
        fis.close();
        return bytesArray;
    }

    public static String takeScreenshot() {
        return Page.takeSnapShot(driver);
    }

    public static void cleanScreenshotAndGif(String scenario) throws IOException {

        if (new File(getScreenshotDir(scenario)).exists())
            FileUtils.cleanDirectory(new File(getScreenshotDir(scenario)));
        if (new File(getGifDir() + scenarioName.replace(" ", "_") + ".gif").exists())
            FileUtils.deleteQuietly(new File(getGifDir() + scenarioName.replace(" ", "_") + ".gif"));
    }

    public static void clearImages() {
        images.clear();
    }

    public static byte[] screenshotInByte() {
        TakesScreenshot ts = (TakesScreenshot) driver;
        return ts.getScreenshotAs(OutputType.BYTES);
    }

    String getStatus() {
        return scenarioName;
    }

    String getScenarioName() {
        return scenarioName;
    }

    public static void setScenarioName(String name) {
        scenarioName = name;
    }

    public static void readCookies() {
        if (!System.getProperty("env").equals("qa"))
            return;

        try {
            File file = new File("Cookies.data");
            FileReader fileReader = new FileReader(file);
            BufferedReader Buffreader = new BufferedReader(fileReader);
            String strline;
            while ((strline = Buffreader.readLine()) != null) {
                StringTokenizer token = new StringTokenizer(strline, ";");
                while (token.hasMoreTokens()) {
                    String name = token.nextToken();
                    String value = token.nextToken();
                    String domain = token.nextToken();
                    String path = token.nextToken();
                    String expiry = token.nextToken(); //keeping expiry as null
                    Boolean isSecure = Boolean.parseBoolean(token.nextToken());
                    Cookie ck = new Cookie(name, value, domain, path, null, isSecure);
                    driver.manage().addCookie(ck); // adding the stored cookie to current session
                }
                driver.navigate().refresh(); //refresh the page after cookies are added
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
