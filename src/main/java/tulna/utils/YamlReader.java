package tulna.utils;

import framework.core.Page;
import framework.logger.LogUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static tulna.Screens.Config.baseDirectory;
import static tulna.Screens.Config.maskImage;


public class YamlReader {
    private static Map<String, Object> result;

    private static YamlReader yamlReader;

    private YamlReader() {
        final String fileName = getPath();
        Yaml yaml = new Yaml();
        InputStream yamlParams = null;
        try {
            yamlParams = new FileInputStream(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        result = (Map<String, Object>) yaml.load(yamlParams);
    }

    public static YamlReader getInstance() {
        if (yamlReader == null) {
            yamlReader = new YamlReader();
        }
        return yamlReader;
    }

    private static String getPath() {
        return baseDirectory + "/tulna.yaml";
    }

    public static Object getValue(String key) {
        return result.get(key);
    }

    public static Map<String, Object> getAllResultsMap() {
        return result;
    }

    public static boolean checkIfYamlFileExists() {
        final String fileName = getPath();
        File file = new File(fileName);
        return file.exists();
    }

    public String fetchValueFromYaml(String screenName, Double zoomFactor) throws FileNotFoundException {
        YamlReader.getInstance();
        Page page = new Page();
        LinkedHashMap maskingData =((LinkedHashMap) ((LinkedHashMap) getValue(maskImage)).get(screenName));
        String maskingRegions = "";
        for (Object key:maskingData.keySet()) {
            List region = (List) maskingData.get(key);
            if (region.size() == 4) {
                maskingRegions =
                        maskingRegions + " rectangle " + region.toString()
                                .replace("[", "").replace("]", "").trim();
            } else {
                try {
                    double maskCoefficient = 0.0;
                    String locator = String.valueOf(region.get(0));
                    try {
                        maskCoefficient = Double.valueOf(String.valueOf(region.get(1))) / 100;
                        LogUtils.INFO(String.format("Mask coefficient %s applied for %s image", maskCoefficient, screenName));
                    } catch (ClassCastException | IndexOutOfBoundsException e) {
                        LogUtils.INFO(String.format("Default mask coefficient applied for %s image", screenName));
                    }
                    HashMap<String, String> locatorMap = new HashMap();
                    locatorMap.put("web", locator);
                    WebElement we = page.getLocator(locatorMap);
                    Point elementLocation = we.getLocation();
                    Dimension elementSize = we.getSize();
                    Long scrollPosition = page.getBrowserScrollPosition();
                    maskingRegions = String.format("%s rectangle %s %s %s %s",
                            maskingRegions,
                            Math.round((elementLocation.getX() - elementSize.width * maskCoefficient) * zoomFactor),
                            Math.round((elementLocation.getY() - scrollPosition - elementSize.height * maskCoefficient) * zoomFactor),
                            Math.round((elementLocation.getX() + elementSize.width * (1 + maskCoefficient)) * zoomFactor),
                            Math.round((elementLocation.getY() - scrollPosition + elementSize.height * (1 + maskCoefficient)) * zoomFactor));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return maskingRegions;
    }


}
