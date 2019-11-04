package tulna.utils;


import tulna.Screens.Config;

import java.io.File;

import static tulna.Screens.Config.*;

public class ScreenPaths {

    private String customPath;
    private String expectedImage;
    private String maskImage;
    private String actualImage;

    private String mergedDiffImage;
    private String diffImage;


    public ScreenPaths(String baseLineImageName) {
        String baselineFolderName = "";
        if (screenshotFolder != null) {
            customPath = screenshotFolder
                    + File.separator + platform;
        } else {
            customPath = platform;
        }

        if (baseLineImageName.contains("/")) {
            baselineFolderName = baseLineImageName.split("/")[0];
            baseLineImageName = baseLineImageName.split("/")[1];
            init(baseLineImageName, baselineFolderName);
        } else {
            init(baseLineImageName, baselineFolderName);
        }
    }

    private void init(String baseLineImageName, String baseLineFolderName) {
        setActualImage(baseLineImageName, baseLineFolderName);
        setExpectedImage(baseLineImageName, baseLineFolderName);
        setDiffImage(baseLineImageName, baseLineFolderName);
        setMaskImage();
        setMergedDiffImage(baseLineImageName, baseLineFolderName);
    }

    private void setExpectedImage(String baseLineImageName, String folderName) {
        this.expectedImage =
                getBasePathForBaseLine(baseLineImageName, folderName) + baseLineImageName
                        + ".jpg";
    }

    private void setMaskImage() {
        this.maskImage =
                baseDirectory + File.separator + platform + "/mask_images/"
                        + Config.maskImage + ".jpg";
    }

    private void setMergedDiffImage(String baseLineImageName, String folderName) {
        this.mergedDiffImage =
                getBasePathForTargetActualImage(baseLineImageName,folderName)+ "difference_"
                        + baseLineImageName + ".jpg";
    }

    private void setDiffImage(String baseLineImageName, String folderName) {
        this.diffImage =
                getBasePathForTargetActualImage(baseLineImageName, folderName)+ "diff_"
                        + baseLineImageName + ".jpg";
    }

    private void setActualImage(String baseLineImageName, String folderName) {
        this.actualImage =
                getBasePathForTargetActualImage(baseLineImageName, folderName)
                        + baseLineImageName + ".jpg";
    }


    private String getBasePathForTargetActualImage(String baseLineImageName, String folderName) {
        return baseDirectory + "/" + customPath
                + "/actual_images/" + folderName + File.separator;
    }

    private String getBasePathForBaseLine(String baseLineImageName, String folderName) {
        return baseDirectory + File.separator + customPath
                + "/baseline_images/" + folderName + File.separator;
    }

    public String getExpectedImage() {
        return expectedImage;
    }

    public String getMaskImage() {
        return maskImage;
    }

    public String getActualImage() {
        return actualImage;
    }

    public String getMergedDiffImage() {
        return mergedDiffImage;
    }

    public String getDiffImage() {
        return diffImage;
    }
}
