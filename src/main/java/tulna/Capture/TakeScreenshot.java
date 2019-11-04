package tulna.Capture;

import tulna.Platforms.PlatformInterface;
import tulna.Platforms.ViewFactory;
import tulna.Platforms.Web;

import java.io.IOException;

import static tulna.Screens.Config.platform;

public class TakeScreenshot {

    private ViewFactory viewFactory = new ViewFactory();

    public void screenCapture(String imagePath) throws IOException {
        if (platform.equals("chrome") || platform.equals("mweb") || platform.equals("web") || platform.equals("web_qa")) {
            new Web().captureScreenShot(imagePath);
        } else {
            PlatformInterface runnerInfo = viewFactory.getPlatform(platform);
            runnerInfo.captureScreenShot(imagePath);
        }
    }
}


