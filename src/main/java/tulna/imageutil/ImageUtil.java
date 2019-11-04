package tulna.imageutil;

import framework.logger.LogUtils;
import org.im4java.core.*;
import org.im4java.process.ArrayListErrorConsumer;
import tulna.utils.YamlReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static tulna.Screens.Validator.hasAttributeInYaml;
import static tulna.Screens.Validator.isYamlPresent;

public class ImageUtil {

    ArrayListErrorConsumer arrayListErrorConsumer = new ArrayListErrorConsumer();

    /**
     * @param actualImage
     * @param expectedImage
     * @param diffImage
     * @return
     * @throws IOException
     * @throws InterruptedException
     * @throws IM4JavaException
     */
    public boolean compareImages(String actualImage, String expectedImage, String diffImage)
            throws IOException, InterruptedException, IM4JavaException {

        IMOps cmpOp = new IMOperation();
        cmpOp.metric("AE");
        cmpOp.fuzz(getFuzzValue(), true);
        cmpOp.addImage(actualImage);
        cmpOp.addImage(expectedImage);
        cmpOp.addImage(diffImage);
        CompareCmd compare = new CompareCmd();
        compare.setErrorConsumer(arrayListErrorConsumer);
        try {
            compare.run(cmpOp);
            return true;
        } catch (Throwable e) {
            try {
                compare.run(cmpOp);
            } catch (Throwable e1) {
                LogUtils.INFO(
                        "Total Pixel Difference:" + arrayListErrorConsumer.getOutput()
                                .get(0));
                LogUtils.ERROR("Exception is ImageUtil>compareImages :" + e1);
            }
            return false;
        }

    }


    /**
     * returns true if the images are similar
     * returns false if the images are not identical
     *
     * @param actualImage
     * @param expectedImage
     * @return
     * @throws IOException
     * @throws InterruptedException
     * @throws IM4JavaException
     */
    public boolean compareImages(String actualImage, String expectedImage)
            throws IOException, InterruptedException, IM4JavaException {
        IMOps cmpOp = new IMOperation();
        cmpOp.metric("AE");
        cmpOp.fuzz(getFuzzValue(), true);
        cmpOp.addImage();
        cmpOp.addImage();
        cmpOp.addImage();
        CompareCmd compare = new CompareCmd();
        compare.setErrorConsumer(arrayListErrorConsumer);
        try {
            compare.run(cmpOp, actualImage, expectedImage);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * @param actualImage actualImagePath
     * @param value       set the ignore % of image pixels
     * @throws IOException
     */
    public boolean compareImages(String expectedImage, String actualImage, String diffImage, int value)
            throws IOException, IM4JavaException, InterruptedException {
        compareImages(actualImage, expectedImage, diffImage);
        long totalImagePixel = getCompleteImagePixel(actualImage);
        if (arrayListErrorConsumer.getOutput().get(0).contains("+")) {
            return false;
        } else {
            LogUtils.INFO(String.format("Parsing message: %s", arrayListErrorConsumer.getOutput().get(0)));
            double totalPixelDifferne = Double.parseDouble(arrayListErrorConsumer.getOutput().get(0).split(" ")[0].replace(",", "."));
            double c = (totalPixelDifferne / totalImagePixel) * 100;
            long finalPercentageDifference = Math.round(c);
            LogUtils.ERROR("Difference in the images is ::" + finalPercentageDifference + "%");
            try {
                if (finalPercentageDifference <= value) {
                    return true;
                }
            } catch (NumberFormatException e) {

            }
            return false;
        }
    }

    /**
     * @param actualImage
     * @param maskImage
     * @throws IOException
     * @throws InterruptedException
     * @throws IM4JavaException
     */

    public void maskImage(String actualImage, String maskImage)
            throws IOException, InterruptedException, IM4JavaException {
        String maskedImage = actualImage;
        IMOperation op = new IMOperation();
        op.addImage(actualImage);
        op.addImage(maskImage);
        op.alpha("on");
        op.compose("DstOut");
        op.composite();
        op.addImage(maskedImage);
        ConvertCmd convert = new ConvertCmd();
        convert.run(op);
    }

    /**
     * @throws InterruptedException
     * @throws IOException
     * @throws IM4JavaException
     */
    public void mergeImagesHorizontally(String expected, String actual, String diffImage,
                                        String mergedImage) throws InterruptedException, IOException, IM4JavaException {
        ConvertCmd cmd1 = new ConvertCmd();
        IMOperation op1 = new IMOperation();
        op1.addImage(expected); // source file
        op1.addImage(actual); // destination file file
        op1.addImage(diffImage);
        //op1.resize(1024,576);
        op1.p_append();
        op1.addImage(mergedImage);
        cmd1.run(op1);
    }


    public int getCompleteImagePixel(String actualImage) throws IOException {
        BufferedImage readImage = null;
        readImage = ImageIO.read(new File(actualImage));
        int h = readImage.getHeight();
        int w = readImage.getWidth();
        return h * w;
    }

    public void maskRegions(String imageToMaskRegion, String screenName)
            throws InterruptedException, IOException, IM4JavaException {
        drawRectangleToIgnore(imageToMaskRegion, screenName);
    }

    private void drawRectangleToIgnore(String imageToMaskRegion,
                                       String screenName) throws IOException, InterruptedException, IM4JavaException {
        ConvertCmd reg = new ConvertCmd();
        IMOperation rep_op = new IMOperation();
        rep_op.addImage(imageToMaskRegion);
        rep_op.fill("Blue");
        String maskingValues = YamlReader.getInstance().fetchValueFromYaml(screenName, zoomFactor());
        if(!maskingValues.equals(""))
            rep_op.draw(maskingValues);
        else
            return;
        rep_op.addImage(imageToMaskRegion);
        reg.run(rep_op);
    }

    public static Double getFuzzValue() throws FileNotFoundException {
        Double fuzz = 5.0;
        if (isYamlPresent() && hasAttributeInYaml("fuzzPercentage")) {
            YamlReader.getInstance();
            fuzz = new Double(YamlReader.getValue("fuzzPercentage").toString());
        }
        return fuzz;
    }

    public static Double zoomFactor() throws FileNotFoundException {
        Double zoomFactor = 2.0;
        if (isYamlPresent() && hasAttributeInYaml("zoomFactor")) {
            YamlReader.getInstance();
            zoomFactor = new Double(YamlReader.getValue("zoomFactor").toString());
        }
        return zoomFactor;
    }
}
