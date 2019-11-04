package tulna.Screens;

import tulna.utils.YamlReader;

import java.util.LinkedHashMap;

import static tulna.Screens.Config.*;

public class Validator {

    public static final String BUILDMODE = "build";
    public static final String FORCEBUILDMODE = "force-build";
    public static final String COMPAREMODE = "compare";
    public static final String ANDROID = "android";
    public static final String CHROME = "chrome";
    public static final String IOS = "ios";
    public static final String SAFARI = "safari";


    public static boolean isBuildMode() {
        return BUILDMODE.equalsIgnoreCase(tulnaMode);
    }

    public static boolean isForceBuildMode() {
        return FORCEBUILDMODE.equalsIgnoreCase(tulnaMode);
    }

    public static boolean isCompareMode() {
        return COMPAREMODE.equalsIgnoreCase(tulnaMode);
    }

    public static boolean isAndroid() {
        return ANDROID.equalsIgnoreCase(platform);
    }

    public static boolean isChrome() {
        return CHROME.equalsIgnoreCase(platform);
    }

    public static boolean isiOS() {
        return IOS.equalsIgnoreCase(platform);
    }

    public static boolean isSafari() {
        return SAFARI.equalsIgnoreCase(platform);
    }

    public static boolean isMaskImagePresent() {
        return maskImage != null && !maskImage.isEmpty();
    }

    public static boolean isFullScreen() {
        final String fullScreenAttr = "fullScreen";
        boolean result = false;
        String fullScreen = null;
        if (isYamlPresent() && hasAttributeInYaml(fullScreenAttr)) {
            fullScreen = YamlReader.getInstance().getValue(fullScreenAttr).toString();
        }
        return fullScreen != null && fullScreen.equalsIgnoreCase("true");
    }

    public static boolean isIgnoreRegion(String baseLineImageName) {
        Object mask = null;
        if (isYamlPresent() && hasAttributeInYaml(maskImage)) {
            YamlReader.getInstance();
            mask = YamlReader.getValue(maskImage);
        }
        return mask != null && ((LinkedHashMap) mask).get(baseLineImageName) != null;
    }

    public static boolean isYamlPresent() {
        return YamlReader.checkIfYamlFileExists();
    }

    public static boolean hasAttributeInYaml(String value) {
        YamlReader.getInstance();
        return YamlReader.getAllResultsMap().containsKey(value);

    }
}
