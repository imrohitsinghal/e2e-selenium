package framework.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public final class ConfigReader {

    public static final String DESKTOP_OS_LINUX = "linux";
    public static final String DESKTOP_OS_MAC = "mac";
    public static final String DESKTOP_OS_WINDOWS = "windows";

    public final String desktopOs;

    private static String getPath() {
        return System.getProperty("user.dir") + "/config.yaml";
    }

    private Map<String, String> readFile() throws FileNotFoundException {
        return (Map<String, String>) new Yaml().load(new FileInputStream(new File(getPath())));
    }

    private String getPropertyValue(String propName, String defaultValue) {
        String prop;
        try {
            prop = readFile().get(propName).trim().toLowerCase();
        } catch (NullPointerException | FileNotFoundException e) {
            prop = defaultValue;
        }
        return prop;
    }

    public ConfigReader() {
        desktopOs = getPropertyValue("desktopOs", DESKTOP_OS_LINUX);
    }
}
