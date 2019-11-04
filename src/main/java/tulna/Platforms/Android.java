package tulna.Platforms;


import com.github.cosysoft.device.android.AndroidDevice;
import com.github.cosysoft.device.android.impl.AndroidDeviceStore;
import com.github.cosysoft.device.image.ImageUtils;
import tulna.imageutil.ImageUtil;
import tulna.utils.Utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.TreeSet;

public class Android {

    private TreeSet<AndroidDevice> devices;
    private AndroidDevice device;
    private Utils util = new Utils();
    ImageUtil imageUtil = new ImageUtil();
    private int deviceSize;

    Android() {
        getDeviceConnected();
        if (!checkIfDevicesAreConnected()) {
            System.out.println("No Devices Connected.... Quiting the execution");
            System.exit(0);
        }
    }

    private void getDeviceConnected() {
        if (System.getenv("UDID") == null || System.getenv("UDID").isEmpty()) {
            devices = AndroidDeviceStore.getInstance()
                    .getDevices();
            deviceSize = devices.size();
            device = devices.pollFirst();
        } else {
            device = AndroidDeviceStore.getInstance().getDeviceBySerial(System.getenv("UDID"));
            if (!device.getName().isEmpty()) {
                deviceSize = 1;
            }
        }
    }

    private boolean checkIfDevicesAreConnected() {
        return deviceSize > 0;
    }

    public String getDeviceName() {
        return device.getName().toString();
    }


    public void captureScreenShot(String imagePath) {
        //util.createDirectory(arg);
        String directoryPath = null;
        File file = new File(imagePath);
        if (file.exists()) {
            System.out.println("BaseLine Image already Exists");
        } else {
            if (!file.isDirectory())
                directoryPath = util.getParentDirectoryFromFile(file);
            util.createDirectory(directoryPath);
            BufferedImage image = device.takeScreenshot();
            ImageUtils.writeToFile(image, imagePath);
        }
    }
}
