package tulna.Platforms;

import framework.core.DriverManager;

import static tulna.Screens.Validator.*;

public class ViewFactory extends DriverManager {

    private Android androidFlow;
//    private iOS iosFlow;


    public PlatformInterface getPlatform(String platform) {
        if (platform == null) {
            return null;
        }
        if (isAndroid()) {
            if (androidFlow == null) {
                return (PlatformInterface) (androidFlow = new Android());
            }
            return (PlatformInterface) androidFlow;
        }
        if (isChrome()) {
            return null;
        }


//        if (isiOS() || isSafari()) {
//            if (iosFlow == null) {
//                return iosFlow = new iOS();
//            }
//            return iosFlow;
//
//        }
        return null;

    }
}
