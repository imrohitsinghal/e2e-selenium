package tulna.Screens;

public class Config {

    public static String tulnaMode = System.getProperty("tulna.mode");
    public static String platform = System.getProperty("tulna.platform");
    public static String maskImage = System.getProperty("tulna.maskimage", "web");
    public static String screenshotFolder = System.getProperty("tulna.screenshot.folder", "Baselines");
    public static String baseDirectory = System.getProperty("user.dir");

}
