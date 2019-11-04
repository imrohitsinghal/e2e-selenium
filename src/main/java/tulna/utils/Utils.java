package tulna.utils;

import framework.logger.LogUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;


public class Utils {

    public void createDirectory(String directoryPath) {
        deleteDirectory(directoryPath);
        File file = new File(directoryPath);
        if (!file.exists()) {
            if (file.mkdirs()) {
                LogUtils.INFO("Baselines Image Directory is Added");
            } else {
                LogUtils.ERROR("Failed to create Baselines image directory!");
            }
        }
    }

    private void deleteDirectory(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            LogUtils.INFO("Deleting Directory :" + path);
            try {
                FileUtils.deleteDirectory(new File(path)); //deletes the whole folder
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            LogUtils.INFO("Deleting File :" + path);
            //it is a simple file. Proceed for deletion
            file.delete();
        }

    }

    public String getParentDirectoryFromFile(File file) {
        return file.getParent();
    }
}
