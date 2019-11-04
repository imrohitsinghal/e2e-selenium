//package tulna.Platforms;
//
//import org.apache.commons.io.FilenameUtils;
//import tulna.Screens.Validator;
//import tulna.utils.CommandPrompt;
//import tulna.utils.Utils;
//
//import io.appium.java_client.ios.IOSDriver;
//import io.appium.java_client.ios.*;
//
//import com.appium.filelocations.FileLocations;
//import com.appium.utils.CapabilityManager;
//import com.appium.utils.OSType;
//import com.thoughtworks.android.AndroidManager;
//import com.thoughtworks.device.Device;
//import com.thoughtworks.device.SimulatorManager;
//import com.thoughtworks.iOS.IOSManager;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class iOS implements PlatformInterface {
//    public ArrayList<String> deviceUDIDiOS = new ArrayList<String>();
//    CommandPrompt commandPrompt = new CommandPrompt();
//    SimulatorManager simulatorManager = new SimulatorManager();
//    Utils utils = new Utils();
//
//    public ArrayList<String> getIOSUDID() throws IOException {
//        List<Device> allAvailableDevices = new IOSManager().getDevices();
//        allAvailableDevices.forEach(iosudid -> {
//            deviceUDIDiOS.add(iosudid.getUdid());
//        });
//        return deviceUDIDiOS;
//    }
//
//    public void captureScreenShot(String imagePath){
//        String directoryPath=null;
//        File file = new File(imagePath);
//        String fileName= file.getName();
//        if(Validator.isBuildMode() && file.exists()){
//            System.out.println("BaseLine Image already Exists");
//        }else{
//            if(!file.isDirectory())
//                directoryPath=utils.getParentDirectoryFromFile(file);
//            utils.createDirectory(directoryPath);
//            try {
//                if(getIOSUDID().size() != 0) {
//                    commandPrompt.runCommand("idevicescreenshot " + imagePath);
//                } else if(simulatorManager.getAllBootedSimulators("iOS").size() != 0) {
//                    fileName= FilenameUtils.removeExtension(fileName);
//                    String UDID = simulatorManager.getAllBootedSimulators("iOS").get(0).getUdid();
//                    simulatorManager.captureScreenshot(UDID, fileName, directoryPath, "png");
//                } else {
//                    System.exit(0);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//}
