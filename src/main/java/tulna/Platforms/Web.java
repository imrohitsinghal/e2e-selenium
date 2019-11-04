package tulna.Platforms;

import framework.core.DriverManager;
import framework.logger.LogUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import tulna.utils.Utils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import static tulna.Screens.Validator.isBuildMode;

public class Web extends DriverManager {

    private Utils util = new Utils();

    public void captureScreenShot(String imagePath) throws IOException {
        String directoryPath = null;
        File file = new File(imagePath);
        if (file.exists() & isBuildMode()) {
            LogUtils.INFO("BaseLine Image already Exists");
        } else {
            if (!file.isDirectory()) {
                directoryPath = util.getParentDirectoryFromFile(file);
                if (!Files.exists(Paths.get(directoryPath))) {
                    util.createDirectory(directoryPath);
                }
            }
            TakesScreenshot scrShot = ((TakesScreenshot) driver);
            File rawScreenshot = scrShot.getScreenshotAs(OutputType.FILE);
            writeImageToFileAdvanced(rawScreenshot, file, "jpg");
        }
    }

    public void writeImageToFile(File imageFile, File fileToOverwrite, String imageExtension) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
        ImageIO.write(newBufferedImage, imageExtension, fileToOverwrite);
    }

    public void writeImageToFileAdvanced(File imageFile, File fileToOverwrite, String imageExtension) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
        Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName(imageExtension);
        ImageWriter writer = iter.next();
        ImageWriteParam iwp = writer.getDefaultWriteParam();
        iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        iwp.setCompressionQuality(1f);   // an integer between 0 and 1
        FileImageOutputStream output = new FileImageOutputStream(fileToOverwrite);
        writer.setOutput(output);
        IIOImage image = new IIOImage(newBufferedImage, null, null);
        writer.write(null, image, iwp);
        writer.dispose();
    }
}
