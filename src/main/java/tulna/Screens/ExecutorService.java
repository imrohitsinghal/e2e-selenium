package tulna.Screens;

import org.im4java.core.IM4JavaException;
import tulna.Capture.TakeScreenshot;
import tulna.imageutil.ImageUtil;
import tulna.utils.ScreenPaths;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static tulna.Screens.Validator.isFullScreen;
import static tulna.Screens.Validator.isIgnoreRegion;

class ExecutorService {

    private ScreenPaths screenPaths;
    private File file;
    private ImageUtil imageUtil;

    ExecutorService() {
        imageUtil = new ImageUtil();
    }

    boolean compareMode(String baseLineImageName, int threshold)
            throws InterruptedException, IOException, IM4JavaException {
        screenPaths = new ScreenPaths(baseLineImageName);
        String actualImage = screenPaths.getActualImage();
        String expectedImage = screenPaths.getExpectedImage();
        String diffImage = screenPaths.getDiffImage();
        screenCaptureAndMaskIfExist(baseLineImageName, actualImage);
        if (threshold > 0 && imageUtil.compareImages(expectedImage,
                actualImage, diffImage)) {
            return true;
        } else {
            if (imageUtil.compareImages(expectedImage,
                    actualImage, diffImage, threshold))
                return true;
        }
        mergerDiffHorizontal(expectedImage, actualImage, diffImage);
        return false;
    }

    boolean buildMode(String baseLineImageName)
            throws InterruptedException, IOException, IM4JavaException {
        screenPaths = new ScreenPaths(baseLineImageName);
        String expectedImage = screenPaths.getExpectedImage();
        screenCaptureAndMaskIfExist(baseLineImageName, expectedImage);
        return checkIfScreenshotsCaptured(expectedImage);
    }

    private boolean checkIfScreenshotsCaptured(String image)
            throws FileNotFoundException {
        File expectedImage = new File(image);
        return expectedImage.exists();

    }

    private void screenCaptureAndMaskIfExist(String baseLineImageName,
                                             String image)
            throws IOException, InterruptedException, IM4JavaException {
        captureScreen(image);
        applyMaskIfPresent(baseLineImageName, image);
    }

    private void applyMaskIfPresent(String baseLineImageName, String image)
            throws IOException, InterruptedException, IM4JavaException {
        if (isIgnoreRegion(baseLineImageName))
            ignoreRegionOnApp(image, baseLineImageName);
        if (!isFullScreen())
            applyMaskImage(image);
    }

    private void ignoreRegionOnApp(String image, String baseLineImageName) throws InterruptedException, IOException, IM4JavaException {
        imageUtil.maskRegions(image, baseLineImageName);
    }

    private void captureScreen(String imagepath) throws IOException {
        new TakeScreenshot().screenCapture(imagepath);
    }

    private void applyMaskImage(String maskedRegionImage) throws InterruptedException, IOException, IM4JavaException {
        imageUtil.maskImage(maskedRegionImage, screenPaths.getMaskImage());
    }

    private void mergerDiffHorizontal(String expectedImage, String actualImage, String diffImage)
            throws InterruptedException, IOException, IM4JavaException {
        imageUtil.mergeImagesHorizontally(expectedImage,
                actualImage, diffImage,
                screenPaths.getMergedDiffImage());
        Thread.sleep(1000);
        file = new File(screenPaths.getDiffImage());
        file.delete();
    }
}
